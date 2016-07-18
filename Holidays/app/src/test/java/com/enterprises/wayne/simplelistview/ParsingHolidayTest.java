package com.enterprises.wayne.simplelistview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ParsingHolidayTest
{
    @Test
    public void testParseDate() throws Exception
    {
        // check that we can parse the date
        String dateStr = "2016-07-04";
        Calendar calendar = ParsingUtils.parseCalendar(dateStr);
        assertNotNull(calendar);
        assertEquals(4, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(6, calendar.get(Calendar.MONTH));
        assertEquals(2016, calendar.get(Calendar.YEAR));
    }

    @Test
    public void testParseHoliday() throws JSONException
    {
        // test parsing the holiday object from a json
        String jsonString = "{\n" +
                "\t\t\t\"name\": \"Independence Day\",\n" +
                "\t\t\t\"date\": \"2016-07-04\",\n" +
                "\t\t\t\"observed\": \"2016-07-04\",\n" +
                "\t\t\t\"public\": true\n" +
                "\t\t}";
        JSONObject jsonObject = new JSONObject(jsonString);
        Holiday holiday = ParsingUtils.parseHoliday(jsonObject);

        assertEquals("Independence Day", holiday.getName());
        assertEquals(true, holiday.isPublic());
        assertEquals(4, holiday.getDate().get(Calendar.DAY_OF_MONTH));
        assertEquals(6, holiday.getDate().get(Calendar.MONTH));
        assertEquals(2016, holiday.getDate().get(Calendar.YEAR));
    }

    @Test
    public void testParseHolidayList() throws JSONException
    {
        // test parsing the whole holidays reponse from a json
        String jsonString =
                "{\n" +
                        "\t\"status\": 200,\n" +
                        "\t\"holidays\": {\n" +
                        "\t\t\"2016-01-01\": [\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"name\": \"Last Day of Kwanzaa\",\n" +
                        "\t\t\t\t\"date\": \"2016-01-01\",\n" +
                        "\t\t\t\t\"observed\": \"2016-01-01\",\n" +
                        "\t\t\t\t\"public\": false\n" +
                        "\t\t\t},\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"name\": \"New Year's Day\",\n" +
                        "\t\t\t\t\"date\": \"2016-01-01\",\n" +
                        "\t\t\t\t\"observed\": \"2016-01-01\",\n" +
                        "\t\t\t\t\"public\": true\n" +
                        "\t\t\t}\n" +
                        "\t\t],\n" +
                        "\t\t\"2016-01-06\": [\n" +
                        "\t\t\t{\n" +
                        "\t\t\t\t\"name\": \"Epiphany\",\n" +
                        "\t\t\t\t\"date\": \"2016-01-06\",\n" +
                        "\t\t\t\t\"observed\": \"2016-01-06\",\n" +
                        "\t\t\t\t\"public\": false\n" +
                        "\t\t\t}\n" +
                        "\t\t]\n" +
                        "\t}\n" +
                        "}";

        final List<Holiday> result = ParsingUtils.parseHolidayList(jsonString);

        assertEquals("Last Day of Kwanzaa", result.get(0).getName());
        assertEquals("New Year's Day", result.get(1).getName());
        assertEquals("Epiphany", result.get(2).getName());

    }

}