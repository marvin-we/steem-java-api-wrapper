#!/bin/bash

set -e
set -o pipefail

cd "$(dirname "$0")"

TEST_MODE=0 #Set to 0 to do a live post.

#preferred repo name
REPO="$(git remote -v|grep origin|grep fetch|sed 's/\t/ /g'|cut -f 2 -d ' ')"
REPO="$(basename $REPO|sed 's/.git$//')"

#override auto detected repo name
#REPO="steem-java-api-wrapper"

TAGS="utopian-io steemdev java steemj steem"

AUTH_FILE="${HOME}/.steem/steem.properties"
STEEM_CLI_POSTER=${HOME}/git/SteemCliPoster/build/libs/SteemCliPoster.jar

when=$(git log -1 --pretty=format:'%ci')

mkdir tmp 2> /dev/null || true
touch tmp/log.old
git log --branches=\* --after="1 day ago" > tmp/log.new
diff tmp/log.new tmp/log.old > /dev/null 2>&1 && exit
if [ ! -s tmp/log.new ]; then exit; fi

echo "Posting new message"
msgfile="tmp/msg.txt"
cp /dev/null "$msgfile"
echo "title: Updates for ${REPO} within the past day. $when"  >> "$msgfile"
echo "tags: ${TAGS}"  >> "$msgfile"
echo "format: markdown"  >> "$msgfile"
echo "" >> "$msgfile"
echo "![SteemJ Logo](https://cdn.steemitimages.com/DQmbwwNH2fZVqPEyNmDFRbimZaPPU3RjcdtEh4uLBDmg1DL/image.png)" >> "$msgfile"
echo "" >> "$msgfile"
echo "## ${REPO}" >> "$msgfile"
echo "Updates for ${REPO} in the past day. $when" >> "$msgfile"
echo "" >> "$msgfile"
echo "" >> "$msgfile"
git log --branches=\* --after="1 day ago" | sed 's/<.*@.*>/[email redacted]/g' | sed 's/^commit /#### commit /g' >> "$msgfile"
echo "" >> "$msgfile"
echo "" >> "$msgfile"
sed -i 's/\&/\&amp;/g' "$msgfile"
sed -i 's/</\&lt;/g' "$msgfile"
sed -i 's/>/\&gt;/g' "$msgfile"
sed -i 's/\t/    /g' "$msgfile"
sed -i 's|  |\&nbsp; |g' "$msgfile"

if [ ${TEST_MODE} = 0 ]; then
	java -jar ${STEEM_CLI_POSTER} \
		--auth-file ${AUTH_FILE} \
		--no-escape \
		--file "$msgfile"
	cp tmp/log.new tmp/log.old
fi

exit 0
