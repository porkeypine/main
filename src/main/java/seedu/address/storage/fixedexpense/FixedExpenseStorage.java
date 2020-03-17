package seedu.address.storage.fixedexpense;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.listmanager.FixedExpenseManager;
import seedu.address.model.listmanager.ReadOnlyFixedExpenseManager;

/**
 * Represents a storage for {@link FixedExpenseManager}.
 */
public interface FixedExpenseStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getFixedExpenseStorageFilePath();

    /**
     * Returns FixedExpenseManager data as a {@link ReadOnlyFixedExpenseManager}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyFixedExpenseManager> readFixedExpenses() throws DataConversionException, IOException;

    /**
     * @see #getFixedExpenseStorageFilePath()
     */
    Optional<ReadOnlyFixedExpenseManager> readFixedExpenses(Path filePath) throws DataConversionException,
            IOException;

    /**
     * Saves the given {@link ReadOnlyFixedExpenseManager} to the storage.
     *
     * @param fixedExpenseManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveFixedExpenses(ReadOnlyFixedExpenseManager fixedExpenseManager) throws IOException;

    /**
     * @see #saveFixedExpenses(ReadOnlyFixedExpenseManager)
     */
    void saveFixedExpenses(
            ReadOnlyFixedExpenseManager fixedExpenseManager, Path filePath) throws IOException;

}
