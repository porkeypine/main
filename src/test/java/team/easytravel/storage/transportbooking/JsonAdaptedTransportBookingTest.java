package team.easytravel.storage.transportbooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static team.easytravel.storage.transportbooking.JsonAdaptedTransportBooking.MISSING_FIELD_MESSAGE_FORMAT;
import static team.easytravel.testutil.Assert.assertThrows;
import static team.easytravel.testutil.transportbooking.TypicalTransportBooking.TRANSPORT_BOOKING_BUS;

import org.junit.jupiter.api.Test;

import team.easytravel.commons.core.time.DateTime;
import team.easytravel.commons.exceptions.IllegalValueException;
import team.easytravel.model.listmanagers.transportbooking.Mode;
import team.easytravel.model.util.attributes.Location;

class JsonAdaptedTransportBookingTest {

    public static final String INVALID_MODE = "T!T!@#E";
    public static final String INVALID_START_LOCATION = "S!NG@P@RE";
    public static final String INVALID_END_LOCATION = "M)&SC&W!";
    public static final String INVALID_START_DATE_TIME = "#21qwe-03-21321";
    public static final String INVALID_END_DATE_TIME = "21qwe-03-21321";

    public static final String VALID_MODE = TRANSPORT_BOOKING_BUS.getMode().value;
    public static final String VALID_START_LOCATION = TRANSPORT_BOOKING_BUS.getStartLocation().value;
    public static final String VALID_END_LOCATION = TRANSPORT_BOOKING_BUS.getEndLocation().value;
    public static final String VALID_START_DATE_TIME = TRANSPORT_BOOKING_BUS.getStartDateTime().getStorageFormat();
    public static final String VALID_END_DATE_TIME = TRANSPORT_BOOKING_BUS.getEndDateTime().getStorageFormat();


    @Test
    public void toModelType_validTransportBookingDetails_returnsActivity() throws Exception {
        JsonAdaptedTransportBooking transportBooking = new JsonAdaptedTransportBooking(TRANSPORT_BOOKING_BUS);
        assertEquals(TRANSPORT_BOOKING_BUS, transportBooking.toModelType());
    }

    @Test
    public void toModelType_invalidMode_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(INVALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String exceptedMessage = Mode.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, exceptedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_nullMode_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(null, VALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String exceptedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Mode.class.getSimpleName());
        assertThrows(IllegalValueException.class, exceptedMessage, transportBooking::toModelType);
    }


    @Test
    public void toModelType_invalidStartLocation_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, INVALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_nullStartLocation_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, null, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }


    @Test
    public void toModelType_invalidEndLocation_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, INVALID_END_LOCATION,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = Location.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_nullEndLocation_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, null,
                        VALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Location.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }


    @Test
    public void toModelType_invalidStartDateTime_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        INVALID_START_DATE_TIME, VALID_END_DATE_TIME);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_nullStartDateTime_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        null, VALID_END_DATE_TIME);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_invalidEndDateTime_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, INVALID_END_DATE_TIME);
        String expectedMessage = DateTime.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }

    @Test
    public void toModelType_nullEndDateTime_throwsIllegalValueException() {
        JsonAdaptedTransportBooking transportBooking =
                new JsonAdaptedTransportBooking(VALID_MODE, VALID_START_LOCATION, VALID_END_LOCATION,
                        VALID_START_DATE_TIME, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, DateTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, transportBooking::toModelType);
    }
}
