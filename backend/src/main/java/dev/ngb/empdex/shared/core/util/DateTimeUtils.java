package dev.ngb.empdex.shared.core.util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * Utility class for date and time operations.
 * Provides common date/time manipulation and formatting methods.
 */
public class DateTimeUtils {
    // Common date formatters
    public static final DateTimeFormatter ISO_DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public static final DateTimeFormatter ISO_DATETIME_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public static final DateTimeFormatter ISO_INSTANT_FORMATTER = DateTimeFormatter.ISO_INSTANT;
    public static final DateTimeFormatter DISPLAY_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter DISPLAY_DATETIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public static final DateTimeFormatter DISPLAY_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    // Time zones
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    public static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();

    private DateTimeUtils() {
        // Utility class - prevent instantiation
    }

    // ==================== Current Time Methods ====================

    /**
     * Get current instant in UTC
     */
    public static Instant now() {
        return Instant.now();
    }

    /**
     * Get current local date in system timezone
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * Get current local date in UTC
     */
    public static LocalDate todayUtc() {
        return LocalDate.now(Clock.systemUTC());
    }

    /**
     * Get current local datetime in system timezone
     */
    public static LocalDateTime nowLocal() {
        return LocalDateTime.now();
    }

    /**
     * Get current local datetime in UTC
     */
    public static LocalDateTime nowUtc() {
        return LocalDateTime.now(Clock.systemUTC());
    }

    // ==================== Conversion Methods ====================

    /**
     * Convert Instant to LocalDateTime in system timezone
     */
    public static LocalDateTime instantToLocalDateTime(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, SYSTEM_ZONE) : null;
    }

    /**
     * Convert Instant to LocalDateTime in UTC
     */
    public static LocalDateTime instantToLocalDateTimeUtc(Instant instant) {
        return instant != null ? LocalDateTime.ofInstant(instant, UTC_ZONE) : null;
    }

    /**
     * Convert LocalDateTime to Instant in system timezone
     */
    public static Instant localDateTimeToInstant(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(SYSTEM_ZONE).toInstant() : null;
    }

    /**
     * Convert LocalDateTime to Instant in UTC
     */
    public static Instant localDateTimeToInstantUtc(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atZone(UTC_ZONE).toInstant() : null;
    }

    /**
     * Convert Instant to LocalDate in system timezone
     */
    public static LocalDate instantToLocalDate(Instant instant) {
        return instant != null ? instant.atZone(SYSTEM_ZONE).toLocalDate() : null;
    }

    /**
     * Convert Instant to LocalDate in UTC
     */
    public static LocalDate instantToLocalDateUtc(Instant instant) {
        return instant != null ? instant.atZone(UTC_ZONE).toLocalDate() : null;
    }

    /**
     * Convert LocalDate to Instant at start of day in system timezone
     */
    public static Instant localDateToInstant(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay(SYSTEM_ZONE).toInstant() : null;
    }

    /**
     * Convert LocalDate to Instant at start of day in UTC
     */
    public static Instant localDateToInstantUtc(LocalDate localDate) {
        return localDate != null ? localDate.atStartOfDay(UTC_ZONE).toInstant() : null;
    }

    // ==================== Database Conversion Methods ====================

    /**
     * Convert Instant to Timestamp
     */
    public static Timestamp instantToTimestamp(Instant instant) {
        return instant != null ? Timestamp.from(instant) : null;
    }

    /**
     * Convert Timestamp to Instant
     */
    public static Instant timestampToInstant(Timestamp timestamp) {
        return timestamp != null ? timestamp.toInstant() : null;
    }

    /**
     * Convert LocalDateTime to Timestamp in system timezone
     */
    public static Timestamp localDateTimeToTimestamp(LocalDateTime localDateTime) {
        return localDateTime != null ? Timestamp.valueOf(localDateTime) : null;
    }

    /**
     * Convert Timestamp to LocalDateTime
     */
    public static LocalDateTime timestampToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

    /**
     * Convert LocalDate to Timestamp at start of day
     */
    public static Timestamp localDateToTimestamp(LocalDate localDate) {
        return localDate != null ? Timestamp.valueOf(localDate.atStartOfDay()) : null;
    }

    /**
     * Convert Timestamp to LocalDate
     */
    public static LocalDate timestampToLocalDate(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime().toLocalDate() : null;
    }

    // ==================== Legacy Date Conversion Methods ====================

    /**
     * Convert Instant to legacy Date
     */
    public static Date instantToDate(Instant instant) {
        return instant != null ? Date.from(instant) : null;
    }

    /**
     * Convert legacy Date to Instant
     */
    public static Instant dateToInstant(Date date) {
        return date != null ? date.toInstant() : null;
    }

    // ==================== Formatting Methods ====================

    /**
     * Format Instant to ISO string
     */
    public static String formatInstant(Instant instant) {
        return instant != null ? ISO_INSTANT_FORMATTER.format(instant) : null;
    }

    /**
     * Format Instant to display string
     */
    public static String formatInstantDisplay(Instant instant) {
        return instant != null ? DISPLAY_DATETIME_FORMATTER.format(instantToLocalDateTime(instant)) : null;
    }

    /**
     * Format LocalDate to ISO string
     */
    public static String formatLocalDate(LocalDate localDate) {
        return localDate != null ? ISO_DATE_FORMATTER.format(localDate) : null;
    }

    /**
     * Format LocalDate to display string
     */
    public static String formatLocalDateDisplay(LocalDate localDate) {
        return localDate != null ? DISPLAY_DATE_FORMATTER.format(localDate) : null;
    }

    /**
     * Format LocalDateTime to ISO string
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? ISO_DATETIME_FORMATTER.format(localDateTime) : null;
    }

    /**
     * Format LocalDateTime to display string
     */
    public static String formatLocalDateTimeDisplay(LocalDateTime localDateTime) {
        return localDateTime != null ? DISPLAY_DATETIME_FORMATTER.format(localDateTime) : null;
    }

    // ==================== Parsing Methods ====================

    /**
     * Parse ISO instant string
     */
    public static Instant parseInstant(String instantString) {
        return instantString != null ? Instant.parse(instantString) : null;
    }

    /**
     * Parse ISO local date string
     */
    public static LocalDate parseLocalDate(String dateString) {
        return dateString != null ? LocalDate.parse(dateString, ISO_DATE_FORMATTER) : null;
    }

    /**
     * Parse ISO local datetime string
     */
    public static LocalDateTime parseLocalDateTime(String dateTimeString) {
        return dateTimeString != null ? LocalDateTime.parse(dateTimeString, ISO_DATETIME_FORMATTER) : null;
    }

    // ==================== Date Range Methods ====================

    /**
     * Get start of day for given date
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date != null ? date.atStartOfDay() : null;
    }

    /**
     * Get end of day for given date
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date != null ? date.atTime(LocalTime.MAX) : null;
    }

    /**
     * Get start of week (Monday) for given date
     */
    public static LocalDate startOfWeek(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)) : null;
    }

    /**
     * Get end of week (Sunday) for given date
     */
    public static LocalDate endOfWeek(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY)) : null;
    }

    /**
     * Get start of month for given date
     */
    public static LocalDate startOfMonth(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.firstDayOfMonth()) : null;
    }

    /**
     * Get end of month for given date
     */
    public static LocalDate endOfMonth(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.lastDayOfMonth()) : null;
    }

    /**
     * Get start of year for given date
     */
    public static LocalDate startOfYear(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.firstDayOfYear()) : null;
    }

    /**
     * Get end of year for given date
     */
    public static LocalDate endOfYear(LocalDate date) {
        return date != null ? date.with(TemporalAdjusters.lastDayOfYear()) : null;
    }

    // ==================== Age Calculation Methods ====================

    /**
     * Calculate age from date of birth
     */
    public static int calculateAge(LocalDate dateOfBirth) {
        return calculateAge(dateOfBirth, today());
    }

    /**
     * Calculate age from date of birth to a specific date
     */
    public static int calculateAge(LocalDate dateOfBirth, LocalDate referenceDate) {
        if (dateOfBirth == null || referenceDate == null) {
            return 0;
        }
        return Period.between(dateOfBirth, referenceDate).getYears();
    }

    /**
     * Calculate age from date of birth to current instant
     */
    public static int calculateAge(LocalDate dateOfBirth, Instant referenceInstant) {
        if (dateOfBirth == null || referenceInstant == null) {
            return 0;
        }
        return calculateAge(dateOfBirth, instantToLocalDate(referenceInstant));
    }

    // ==================== Duration Methods ====================

    /**
     * Calculate days between two dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Calculate days between two instants
     */
    public static long daysBetween(Instant startInstant, Instant endInstant) {
        if (startInstant == null || endInstant == null) {
            return 0;
        }
        return ChronoUnit.DAYS.between(startInstant, endInstant);
    }

    /**
     * Calculate hours between two instants
     */
    public static long hoursBetween(Instant startInstant, Instant endInstant) {
        if (startInstant == null || endInstant == null) {
            return 0;
        }
        return ChronoUnit.HOURS.between(startInstant, endInstant);
    }

    /**
     * Calculate minutes between two instants
     */
    public static long minutesBetween(Instant startInstant, Instant endInstant) {
        if (startInstant == null || endInstant == null) {
            return 0;
        }
        return ChronoUnit.MINUTES.between(startInstant, endInstant);
    }

    /**
     * Calculate seconds between two instants
     */
    public static long secondsBetween(Instant startInstant, Instant endInstant) {
        if (startInstant == null || endInstant == null) {
            return 0;
        }
        return ChronoUnit.SECONDS.between(startInstant, endInstant);
    }

    // ==================== Business Logic Methods ====================

    /**
     * Check if a date is today
     */
    public static boolean isToday(LocalDate date) {
        return date != null && date.equals(today());
    }

    /**
     * Check if a date is in the past
     */
    public static boolean isPast(LocalDate date) {
        return date != null && date.isBefore(today());
    }

    /**
     * Check if a date is in the future
     */
    public static boolean isFuture(LocalDate date) {
        return date != null && date.isAfter(today());
    }

    /**
     * Check if an instant is in the past
     */
    public static boolean isPast(Instant instant) {
        return instant != null && instant.isBefore(now());
    }

    /**
     * Check if an instant is in the future
     */
    public static boolean isFuture(Instant instant) {
        return instant != null && instant.isAfter(now());
    }

    /**
     * Check if a date is within a range (inclusive)
     */
    public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return date != null && startDate != null && endDate != null && !date.isBefore(startDate) && !date.isAfter(endDate);
    }

    /**
     * Check if an instant is within a range (inclusive)
     */
    public static boolean isBetween(Instant instant, Instant startInstant, Instant endInstant) {
        return instant != null && startInstant != null && endInstant != null && !instant.isBefore(startInstant) && !instant.isAfter(endInstant);
    }

    /**
     * Check if a date is a working day (Monday to Friday)
     */
    public static boolean isWorkingDay(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    /**
     * Check if a date is a weekend
     */
    public static boolean isWeekend(LocalDate date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    // ==================== Time Zone Methods ====================

    /**
     * Convert instant to local datetime in specified timezone
     */
    public static LocalDateTime instantToLocalDateTime(Instant instant, ZoneId zoneId) {
        return instant != null && zoneId != null ? LocalDateTime.ofInstant(instant, zoneId) : null;
    }

    /**
     * Convert local datetime to instant in specified timezone
     */
    public static Instant localDateTimeToInstant(LocalDateTime localDateTime, ZoneId zoneId) {
        return localDateTime != null && zoneId != null ? localDateTime.atZone(zoneId).toInstant() : null;
    }

    /**
     * Get current instant in specified timezone
     */
    public static Instant now(ZoneId zoneId) {
        return zoneId != null ? Instant.now(Clock.system(zoneId)) : now();
    }

    // ==================== Validation Methods ====================

    /**
     * Check if a string is a valid ISO date
     */
    public static boolean isValidIsoDate(String dateString) {
        if (dateString == null || dateString.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDate.parse(dateString, ISO_DATE_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if a string is a valid ISO datetime
     */
    public static boolean isValidIsoDateTime(String dateTimeString) {
        if (dateTimeString == null || dateTimeString.trim().isEmpty()) {
            return false;
        }
        try {
            LocalDateTime.parse(dateTimeString, ISO_DATETIME_FORMATTER);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check if a string is a valid ISO instant
     */
    public static boolean isValidIsoInstant(String instantString) {
        if (instantString == null || instantString.trim().isEmpty()) {
            return false;
        }
        try {
            Instant.parse(instantString);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
