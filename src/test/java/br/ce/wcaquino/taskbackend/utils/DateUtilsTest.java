package br.ce.wcaquino.taskbackend.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTest {
    @Test
    public void shouldReturnTrueForFutureDates() {
        LocalDate date = LocalDate.of(2030,01,01);
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void shouldReturnTrueForPastDates() {
        LocalDate date = LocalDate.of(2010,01,01);
        Assert.assertFalse(DateUtils.isEqualOrFutureDate(date));
    }

    @Test
    public void shouldReturnTrueForActualDates() {
        LocalDate date = LocalDate.now();
        Assert.assertTrue(DateUtils.isEqualOrFutureDate(date));
    }
}