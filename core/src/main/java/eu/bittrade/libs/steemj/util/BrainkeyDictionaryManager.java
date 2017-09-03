package eu.bittrade.libs.steemj.util;

import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * As the {@link eu.bittrade.libs.steemj.util.KeyGenerator KeyGenerator} class
 * can be initiated several times with each of those instances needing the
 * dictionary, this class has been created to have the dictionary in memory for
 * only one time.
 * 
 * @author <a href="http://steemit.com/@dez1337">dez1337</a>
 */
class BrainkeyDictionaryManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(BrainkeyDictionaryManager.class);

    private static final String DICTIONARY_FILE_NAME = "dictionary.txt";
    private static final String DICTIONARY_DELIMITER = ",";

    private static BrainkeyDictionaryManager brainkeyDictionaryManagerInstance;
    private String[] brainKeyDictionary;

    /**
     * Create a a new BrainkeyDictionaryManager instance. This method will load
     * and split the {@link #DICTIONARY_FILE_NAME}.
     * 
     * @throws IOException
     *             If there is a problem loading the file.
     */
    private BrainkeyDictionaryManager() throws IOException {
        FileReader fileReader = new FileReader(getClass().getClassLoader().getResource(DICTIONARY_FILE_NAME).getFile());
        try {
            this.brainKeyDictionary = IOUtils.toString(fileReader).split(DICTIONARY_DELIMITER);
        } finally {
            fileReader.close();
        }
    }

    /**
     * Get the brainkey dictionary as an array of Strings.
     * 
     * @return The brainkey dictionary
     */
    public String[] getBrainKeyDictionary() {
        return brainKeyDictionary;
    }

    /**
     * Get the current BrainkeyDictionaryManager instance.
     * 
     * @return The current BrainkeyDictionaryManager instance.
     */
    public static BrainkeyDictionaryManager getInstance() {
        try {
            if (brainkeyDictionaryManagerInstance == null) {
                brainkeyDictionaryManagerInstance = new BrainkeyDictionaryManager();
            }
        } catch (IOException e) {
            // This should never happen.
            LOGGER.error("Could not create a new BrainkeyDictionaryManager instance.", e);
        }

        return brainkeyDictionaryManagerInstance;
    }
}
