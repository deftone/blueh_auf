package de.deftone.blueh_auf.biotopVernetzung;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class) //startet Spring aber mit leerem appl context
@Import(BluehService.class)
        //fuegt diesen Service als bean dem appl. context hinzu
class BluehServiceTest {

    @Autowired
    private BluehService bluehService;

    //der service braucht auch dieses gemockte repo
    @MockBean
    private BluehEventRepo repoMock;

    @Test
    void addressStringOk_null() {
        assertFalse(bluehService.addressStringOk(null));
    }

    @Test
    void addressStringOk_empty() {
        assertFalse(bluehService.addressStringOk(""));
    }

    @Test
    void addressStringOk_blank() {
        assertFalse(bluehService.addressStringOk(" "));
    }

    @Test
    void addressStringOk_wrongAddress_1() {
        assertFalse(bluehService.addressStringOk("keine hausnr"));
    }

    @Test
    void addressStringOk_wrongAddress_2() {
        assertFalse(bluehService.addressStringOk("keine hausnr 1234 ort"));
    }

    @Test
    void addressStringOk_addressOK_1() {
        assertTrue(bluehService.addressStringOk("strasse mit hausnr 4"));
    }

    @Test
    void addressStringOk_addressOK_2() {
        assertTrue(bluehService.addressStringOk("strasse-mit-hausnr. 44"));
    }

    @Test
    void addressStringOk_addressOK_3() {
        assertTrue(bluehService.addressStringOk("tolle str. 103"));
    }

    @Test
    void addressStringOk_addressOK_4() {
        assertTrue(bluehService.addressStringOk("miüt Umäu staße 103"));
    }

    @Test
    void coordinateStringOk_null() {
        assertFalse(bluehService.coordinateStringOk(null));
    }

    @Test
    void coordinateStringOk_empty() {
        assertFalse(bluehService.coordinateStringOk(""));
    }

    @Test
    void coordinateStringOk_blank() {
        assertFalse(bluehService.coordinateStringOk(" "));
    }

    @Test
    void coordinateStringOk_wrongCoord_1() {
        assertFalse(bluehService.coordinateStringOk("keine Koordin"));
    }

    @Test
    void coordinateStringOk_wrongCoord_2() {
        assertFalse(bluehService.coordinateStringOk("33.5, 6.3"));
    }

    @Test
    void coordinateStringOk_CoordOK_1() {
        assertTrue(bluehService.coordinateStringOk(" 49.3,8.3 "));
    }

    @Test
    void coordinateStringOk_CoordOK_2() {
        assertTrue(bluehService.coordinateStringOk(" 49.3,   8.3 "));
    }

    @Test
    void coordinateStringOk_CoordOK_3() {
        assertTrue(bluehService.coordinateStringOk(" 49.123456789123456, 8.123456789123456789"));
    }

    @Test
    void coordinateStringOk_CoordOK_4() {
        assertTrue(bluehService.coordinateStringOk(" 49,3,8,3 "));
    }

    @Test
    void checkCoordinates_ausserhalb_1() {
        assertEquals("Die Adresse/Koordinate liegt nicht in Roßdorf. Bitte nochmal versuchen.",
                bluehService.checkCoordinates(new GeoLocation("49.86787289368089, 8.719310824329499")));
    }

    @Test
    void checkCoordinates_ausserhalb_2() {
        assertEquals("Die Adresse/Koordinate liegt nicht in Roßdorf. Bitte nochmal versuchen.",
                bluehService.checkCoordinates(new GeoLocation("49.85358902260791, 8.72248651177146")));
    }

    @Test
    void checkCoordinates_ausserhalb_3() {
        assertEquals("Die Adresse/Koordinate liegt nicht in Roßdorf. Bitte nochmal versuchen.",
                bluehService.checkCoordinates(new GeoLocation("49.846189527125766, 8.745798944584044")));
    }

    @Test
    void checkCoordinates_ausserhalb_4() {
        assertEquals("Die Adresse/Koordinate liegt nicht in Roßdorf. Bitte nochmal versuchen.",
                bluehService.checkCoordinates(new GeoLocation("49.849938448013926, 8.793289906784288")));
    }

    @Test
    void checkCoordinates_ausserhalb_5() {
        assertEquals("Die Adresse/Koordinate liegt nicht in Roßdorf. Bitte nochmal versuchen.",
                bluehService.checkCoordinates(new GeoLocation("49.86536072731143, 8.8060959608264595")));
    }

    @Test
    void checkCoordinates_ausserhalb_6() {
        assertEquals("Die Adresse/Koordinate liegt nicht in Roßdorf. Bitte nochmal versuchen.",
                bluehService.checkCoordinates(new GeoLocation("49.87711387511391, 8.75993264508611")));
    }

    @Test
    void checkCoordinates_innerhalb_1() {
        assertNull(bluehService.checkCoordinates(new GeoLocation("49.8509, 8.7309")));
    }

    @Test
    void checkCoordinates_innerhalb_2() {
        assertNull(bluehService.checkCoordinates(new GeoLocation("49.8769, 8.8059")));
    }
}