package eu.bittrade.libs.steemj.base.models;

import java.io.IOException;
import java.security.InvalidParameterException;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.bittrade.libs.steemj.exceptions.SteemInvalidTransactionException;
import eu.bittrade.libs.steemj.util.SteemJUtils;

/**
 * This class is the java implementation of the Steem "version" object.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
@JsonDeserialize
@JsonSerialize
public class Version extends BlockHeaderExtensions {
    @JsonIgnore
    private int versionNumber;

    /**
     * This class represents the basic versioning scheme of the Steem
     * blockchain. All versions are a triple consisting of a major version,
     * hardfork version, and release version. It allows easy comparison between
     * versions. A version is a read only object.
     * 
     * @param majorVersion
     *            The major version to set.
     * @param hardforkVersion
     *            The hardfork version to set.
     * @param releaseVersion
     *            The release version to set.
     */
    public Version(byte majorVersion, byte hardforkVersion, short releaseVersion) {
        this.versionNumber = (0 | majorVersion) << 8;
        this.versionNumber = (this.versionNumber | hardforkVersion) << 16;
        this.versionNumber = this.versionNumber | releaseVersion;
    }

    /**
     * Like {@link #Version(byte, byte, short)}, but this constructor allows to
     * provide the version in its String representation (e.g. 0.19.2).
     * 
     * @param versionAsString
     *            The version to set.
     */
    @JsonCreator
    public Version(String versionAsString) {
        if (!versionAsString.contains(".") && !versionAsString.contains("_")) {
            throw new InvalidParameterException(
                    "The given version does not contain the expected delemitter '.' or '_'.");
        }

        String[] versionParts;

        if (versionAsString.contains(".")) {
            versionParts = versionAsString.split("\\.");
        } else {
            versionParts = versionAsString.split("_");
        }

        if (versionParts.length != 3) {
            throw new InvalidParameterException(
                    "The String representation of a version does not contain all 3 required parts (major, hardfork and release number).");
        }

        int major = Integer.parseInt(versionParts[0]);
        int hardfork = Integer.parseInt(versionParts[1]);
        int revision = Integer.parseInt(versionParts[2]);

        if (major > 0xFF) {
            throw new InvalidParameterException("The Major version is out of range.");
        } else if (hardfork > 0xFF) {
            throw new InvalidParameterException("The Hardfork version is out of range.");
        } else if (revision > 0xFFFF) {
            throw new InvalidParameterException("The Revision version is out of range.");
        }

        this.versionNumber = 0 | (major << 24) | (hardfork << 16) | revision;
    }

    @Override
    public byte[] toByteArray() throws SteemInvalidTransactionException {
        try (ByteArrayOutputStream serializedVersion = new ByteArrayOutputStream()) {
            serializedVersion.write(SteemJUtils.transformIntToByteArray(versionNumber));

            return serializedVersion.toByteArray();
        } catch (IOException e) {
            throw new SteemInvalidTransactionException(
                    "A problem occured while transforming the version object into a byte array.", e);
        }
    }

    @Override
    @JsonValue
    public String toString() {
        StringBuilder versionToStingBuilder = new StringBuilder();

        versionToStingBuilder.append(((this.versionNumber >> 24) & 0x000000FF));
        versionToStingBuilder.append(".");
        versionToStingBuilder.append(((this.versionNumber >> 16) & 0x000000FF));
        versionToStingBuilder.append(".");
        versionToStingBuilder.append((this.versionNumber & 0x0000FFFF));

        return versionToStingBuilder.toString();
    }
}
