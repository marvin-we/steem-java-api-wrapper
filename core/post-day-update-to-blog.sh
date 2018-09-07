#!/bin/bash

set -e
set -o pipefail

cd "$(dirname "$0")"

when=$(git log -1 --pretty=format:'%ci')

mkdir tmp 2> /dev/null || true
touch tmp/log.old
git log --branches=\* --after="1 day ago" > tmp/log.new
diff tmp/log.new tmp/log.old > /dev/null 2>&1 && exit
if [ ! -s tmp/log.new ]; then exit; fi

echo "Posting new message"
msgfile="tmp/msg.txt"
cp /dev/null "$msgfile"
echo "title: Updates for $(basename $(pwd)) within the past day. $when"  >> "$msgfile"
echo "tags: utopian-io steemdev java steemj steem"  >> "$msgfile"
echo "format: markdown"  >> "$msgfile"
echo "" >> "$msgfile"
echo "![SteemJ Logo](https://cdn.steemitimages.com/DQmbwwNH2fZVqPEyNmDFRbimZaPPU3RjcdtEh4uLBDmg1DL/image.png)" >> "$msgfile"
echo "" >> "$msgfile"
echo "## $(basename $(pwd))" >> "$msgfile"
echo "Updates for $(basename $(pwd)) in the past day. $when" >> "$msgfile"
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
java -jar ~/git/SteemCliPoster/build/libs/SteemCliPoster.jar \
	--auth-file ~/.steem/steem.properties \
	--no-escape \
	--file "$msgfile"
cp tmp/log.new tmp/log.old

