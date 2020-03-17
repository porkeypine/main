package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.activity.Activity;
import seedu.address.model.fixedexpense.FixedExpense;
import seedu.address.model.listmanager.ActivityManager;
import seedu.address.model.listmanager.FixedExpenseManager;
import seedu.address.model.listmanager.PackingListManager;
import seedu.address.model.listmanager.ReadOnlyActivityManager;
import seedu.address.model.listmanager.ReadOnlyFixedExpenseManager;
import seedu.address.model.listmanager.ReadOnlyPackingListManager;
import seedu.address.model.listmanager.ReadOnlyTransportBookingManager;
import seedu.address.model.listmanager.TransportBookingManager;
import seedu.address.model.packinglistitem.PackingListItem;
import seedu.address.model.person.Person;
import seedu.address.model.transportbooking.TransportBooking;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final TransportBookingManager transportBookingManager;
    private final FixedExpenseManager fixedExpenseManager;
    private final PackingListManager packingListManager;
    private final ActivityManager activityManager;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<TransportBooking> filteredTransportBookingList;
    private final FilteredList<FixedExpense> filteredFixedExpenseList;
    private final FilteredList<PackingListItem> filteredPackingList;
    private final FilteredList<Activity> filteredActivityList;

    /**
     * Initializes a ModelManager with the given managers and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTransportBookingManager transportBookingManager,
                        ReadOnlyFixedExpenseManager fixedExpenseManager, ReadOnlyPackingListManager packingListManager,
                        ReadOnlyActivityManager activityManager, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.transportBookingManager = new TransportBookingManager(transportBookingManager);
        this.fixedExpenseManager = new FixedExpenseManager(fixedExpenseManager);
        this.packingListManager = new PackingListManager(packingListManager);
        this.activityManager = new ActivityManager(activityManager);
        this.userPrefs = new UserPrefs(userPrefs);

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTransportBookingList = new FilteredList<>(this.transportBookingManager.getTransportBookings());
        filteredFixedExpenseList = new FilteredList<>(this.fixedExpenseManager.getFixedExpenseList());
        filteredPackingList = new FilteredList<>(this.packingListManager.getPackingList());
        filteredActivityList = new FilteredList<>(this.activityManager.getActivityList());
    }

    public ModelManager() {
        this(new AddressBook(), new TransportBookingManager(), new FixedExpenseManager(), new PackingListManager(),
                new ActivityManager(),
                new UserPrefs());
    }

    // Temporary constructor
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyTransportBookingManager transportBookingManager,
                        ReadOnlyFixedExpenseManager fixedExpenseManager, ReadOnlyUserPrefs userPrefs) {
        this(addressBook, transportBookingManager, fixedExpenseManager, new PackingListManager(),
                new ActivityManager(), userPrefs);
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    // TODO: Implement the methods below
    // ========== TransportBookingManager ==========

    @Override
    public boolean hasTransportBooking(TransportBooking target) {
        requireNonNull(target);
        return transportBookingManager.hasTransportBooking(target);
    }

    @Override
    public void deleteTransportBooking(TransportBooking toDelete) {
        transportBookingManager.removeTransportBooking(toDelete);
    }

    @Override
    public void addTransportBooking(TransportBooking toAdd) {
        transportBookingManager.addTransportBooking(toAdd);
        updateFilteredTransportBookingList(PREDICATE_SHOW_ALL_TRANSPORT_BOOKINGS);
    }

    @Override
    public void setTransportBooking(TransportBooking target, TransportBooking edited) {
        requireAllNonNull(target, edited);
        transportBookingManager.setTransportBooking(target, edited);
    }

    @Override
    public ObservableList<TransportBooking> getFilteredTransportBookingList() {
        return filteredTransportBookingList;
    }

    @Override
    public void updateFilteredTransportBookingList(Predicate<TransportBooking> predicate) {
        requireNonNull(predicate);
        filteredTransportBookingList.setPredicate(predicate);
    }

    // ========== FixedExpenseManager ==========

    @Override
    public boolean hasFixedExpense(FixedExpense target) {
        requireNonNull(target);
        return fixedExpenseManager.hasFixedExpense(target);
    }

    @Override
    public void deleteFixedExpense(FixedExpense toDelete) {
        fixedExpenseManager.removeFixedExpense(toDelete);
    }

    @Override
    public void addFixedExpense(FixedExpense toAdd) {
        fixedExpenseManager.addFixedExpense(toAdd);
        updateFilteredFixedExpenseList(PREDICATE_SHOW_ALL_FIXED_EXPENSES);
    }

    @Override
    public void setFixedExpense(FixedExpense target, FixedExpense edited) {
        requireAllNonNull(target, edited);
        fixedExpenseManager.setFixedExpense(target, edited);
    }

    @Override
    public ObservableList<FixedExpense> getFilteredFixedExpenseList() {
        return filteredFixedExpenseList;
    }

    @Override
    public void updateFilteredFixedExpenseList(Predicate<FixedExpense> predicate) {
        requireNonNull(predicate);
        filteredFixedExpenseList.setPredicate(predicate);
    }

    // ========== PackingListManager ==========

    @Override
    public boolean hasPackingListItem(PackingListItem target) {
        return false;
    }

    @Override
    public void deletePackingListItem(PackingListItem toDelete) {

    }

    @Override
    public void addPackingListItem(PackingListItem toAdd) {

    }

    @Override
    public void setPackingListItem(PackingListItem target, PackingListItem edited) {

    }

    @Override
    public ObservableList<PackingListItem> getFilteredPackingList() {
        return null;
    }

    @Override
    public void updateFilteredPackingList(Predicate<PackingListItem> predicate) {

    }

    // ========== ActivityManager ==========

    @Override
    public boolean hasActivity(Activity target) {
        requireNonNull(target);
        return activityManager.hasActivity(target);
    }

    @Override
    public void deleteActivity(Activity toDelete) {
        activityManager.removeActivity(toDelete);

    }

    @Override
    public void addActivity(Activity toAdd) {
        activityManager.addActivity(toAdd);
        updateFilteredActivityList(PREDICATE_SHOW_ALL_ACTIVITIES);
    }

    @Override
    public void setActivity(Activity target, Activity edited) {
        requireAllNonNull(target, edited);
        activityManager.setActivity(target, edited);
    }

    @Override
    public ObservableList<Activity> getFilteredActivityList() {
        return filteredActivityList;
    }

    @Override
    public void updateFilteredActivityList(Predicate<Activity> predicate) {
        requireNonNull(predicate);
        filteredActivityList.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

}
