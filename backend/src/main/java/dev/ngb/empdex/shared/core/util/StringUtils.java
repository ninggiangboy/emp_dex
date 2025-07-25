package dev.ngb.empdex.shared.core.util;

import java.security.SecureRandom;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringUtils {
    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Username validation pattern (alphanumeric, dots, underscores, hyphens)
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9._-]+$");

    // Phone pageNumber validation pattern (basic international format)
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[1-9]\\d{1,14}$");

    // URL validation pattern
    private static final Pattern URL_PATTERN = Pattern.compile("^(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$");

    // Password strength pattern (at least 8 chars, 1 uppercase, 1 lowercase, 1 digit)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$");

    /**
     * Checks if a string is null or empty
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * Checks if a string is null, empty, or contains only whitespace
     */
    public static boolean isNullOrBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * Returns the string if not null, otherwise returns empty string
     */
    public static String nullToEmpty(String str) {
        return str != null ? str : "";
    }

    /**
     * Returns the string if not null, otherwise returns the default value
     */
    public static String nullToDefault(String str, String defaultValue) {
        return str != null ? str : defaultValue;
    }

    /**
     * Truncates a string to the specified length, adding ellipsis if truncated
     */
    public static String truncate(String str, int maxLength) {
        if (str == null)
            return null;
        if (str.length() <= maxLength)
            return str;
        return str.substring(0, maxLength - 3) + "...";
    }

    /**
     * Capitalizes the first letter of a string
     */
    public static String capitalize(String str) {
        if (isNullOrEmpty(str))
            return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    /**
     * Converts a string to title case (capitalizes first letter of each word)
     */
    public static String toTitleCase(String str) {
        if (isNullOrEmpty(str))
            return str;
        return Stream.of(str.split("\\s+")).map(StringUtils::capitalize).collect(Collectors.joining(" "));
    }

    /**
     * Removes all whitespace from a string
     */
    public static String removeWhitespace(String str) {
        if (str == null)
            return null;
        return str.replaceAll("\\s+", "");
    }

    /**
     * Normalizes whitespace (replaces multiple spaces with single space)
     */
    public static String normalizeWhitespace(String str) {
        if (str == null)
            return null;
        return str.replaceAll("\\s+", " ").trim();
    }

    /**
     * Validates email format
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validates username format (alphanumeric, dots, underscores, hyphens)
     */
    public static boolean isValidUsername(String username) {
        return username != null && USERNAME_PATTERN.matcher(username).matches();
    }

    /**
     * Validates phone pageNumber format (basic international format)
     */
    public static boolean isValidPhoneNumber(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }

    /**
     * Validates URL format
     */
    public static boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    /**
     * Validates password strength (at least 8 chars, 1 uppercase, 1 lowercase, 1 digit)
     */
    public static boolean isValidPassword(String password) {
        return password != null && PASSWORD_PATTERN.matcher(password).matches();
    }

    /**
     * Checks if a string contains only digits
     */
    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }

    /**
     * Checks if a string contains only letters
     */
    public static boolean isAlpha(String str) {
        return str != null && str.matches("[a-zA-Z]+");
    }

    /**
     * Checks if a string contains only letters and digits
     */
    public static boolean isAlphanumeric(String str) {
        return str != null && str.matches("[a-zA-Z0-9]+");
    }

    /**
     * Generates a random alphanumeric string of specified length
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(index));
        }
        return sb.toString();
    }

    /**
     * Generates a random digital string of specified length use SecureRandom
     */
    public static String generateSecureRandomDigits(int length) {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = secureRandom.nextInt(10);  // 0–9
            otp.append(digit);
        }

        return otp.toString();
    }

    /**
     * Masks sensitive data (like email or phone) for display
     */
    public static String maskSensitiveData(String data, char maskChar) {
        if (isNullOrEmpty(data) || data.length() < 3)
            return data;

        int visibleChars = Math.max(1, data.length() / 3);
        String visible = data.substring(0, visibleChars);
        String masked = String.valueOf(maskChar).repeat(data.length() - visibleChars);
        return visible + masked;
    }

    /**
     * Masks email address (shows first and last character, masks the rest)
     */
    public static String maskEmail(String email) {
        if (!isValidEmail(email))
            return email;

        int atIndex = email.indexOf('@');
        if (atIndex <= 1)
            return email;

        String localPart = email.substring(0, atIndex);
        String domain = email.substring(atIndex);

        String maskedLocal = localPart.charAt(0) + "*".repeat(localPart.length() - 2) + localPart.charAt(localPart.length() - 1);

        return maskedLocal + domain;
    }

    /**
     * Masks phone pageNumber (shows last 4 digits)
     */
    public static String maskPhoneNumber(String phone) {
        if (!isValidPhoneNumber(phone))
            return phone;

        if (phone.length() <= 4)
            return phone;

        return "*".repeat(phone.length() - 4) + phone.substring(phone.length() - 4);
    }

    /**
     * Converts a string to camelCase
     */
    public static String toCamelCase(String str) {
        if (isNullOrEmpty(str))
            return str;

        String[] words = str.toLowerCase().split("[\\s_-]+");
        if (words.length == 0)
            return str;

        StringBuilder result = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                result.append(capitalize(words[i]));
            }
        }
        return result.toString();
    }

    /**
     * Converts a string to kebab-case
     */
    public static String toKebabCase(String str) {
        if (isNullOrEmpty(str))
            return str;
        return str.toLowerCase().replaceAll("[\\s_]+", "-").replaceAll("[^a-z0-9-]", "").replaceAll("-+", "-").replaceAll("^-|-$", "");
    }

    /**
     * Converts a string to snake_case
     */
    public static String toSnakeCase(String str) {
        if (isNullOrEmpty(str))
            return str;
        return str.toLowerCase().replaceAll("[\\s-]+", "_").replaceAll("[^a-z0-9_]", "").replaceAll("_+", "_").replaceAll("^_|_$", "");
    }

    /**
     * Extracts domain from email address
     */
    public static String extractDomainFromEmail(String email) {
        if (!isValidEmail(email))
            return null;
        int atIndex = email.indexOf('@');
        return atIndex > 0 ? email.substring(atIndex + 1) : null;
    }

    /**
     * Extracts local part from email address
     */
    public static String extractLocalPartFromEmail(String email) {
        if (!isValidEmail(email))
            return null;
        int atIndex = email.indexOf('@');
        return atIndex > 0 ? email.substring(0, atIndex) : null;
    }

    /**
     * Counts occurrences of a substring in a string
     */
    public static int countOccurrences(String str, String substring) {
        if (isNullOrEmpty(str) || isNullOrEmpty(substring))
            return 0;

        int count = 0;
        int lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = str.indexOf(substring, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += substring.length();
            }
        }
        return count;
    }

    /**
     * Reverses a string
     */
    public static String reverse(String str) {
        if (str == null)
            return null;
        return new StringBuilder(str).reverse().toString();
    }

    /**
     * Checks if a string is a palindrome
     */
    public static boolean isPalindrome(String str) {
        if (str == null)
            return false;
        String cleaned = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        return cleaned.equals(reverse(cleaned));
    }

    /**
     * Removes HTML tags from a string
     */
    public static String removeHtmlTags(String str) {
        if (str == null)
            return null;
        return str.replaceAll("<[^>]*>", "");
    }

    /**
     * Escapes HTML special characters
     */
    public static String escapeHtml(String str) {
        if (str == null)
            return null;
        return str.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    /**
     * Unescapes HTML special characters
     */
    public static String unescapeHtml(String str) {
        if (str == null)
            return null;
        return str.replace("&amp;", "&").replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\"").replace("&#39;", "'");
    }

    /**
     * Converts a string to a safe filename
     */
    public static String toSafeFilename(String str) {
        if (isNullOrEmpty(str))
            return "unnamed";
        return str.replaceAll("[^a-zA-Z0-9._-]", "_").replaceAll("_+", "_").replaceAll("^_|_$", "");
    }

    /**
     * Generates initials from a full name
     */
    public static String generateInitials(String fullName) {
        if (isNullOrEmpty(fullName))
            return "";

        return Stream.of(fullName.split("\\s+")).filter(word -> !word.isEmpty()).map(word -> word.substring(0, 1).toUpperCase()).limit(3) // Limit to 3 initials
                .collect(Collectors.joining());
    }

    /**
     * Formats a pageNumber with thousand separators
     */
    public static String formatNumber(long number) {
        return String.format("%,d", number);
    }

    /**
     * Formats a decimal pageNumber with specified decimal places
     */
    public static String formatDecimal(double number, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", number);
    }

    /**
     * Checks if a string starts with any of the given prefixes
     */
    public static boolean startsWithAny(String str, String... prefixes) {
        if (str == null || prefixes == null)
            return false;
        for (String prefix : prefixes) {
            if (prefix != null && str.startsWith(prefix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if a string ends with any of the given suffixes
     */
    public static boolean endsWithAny(String str, String... suffixes) {
        if (str == null || suffixes == null)
            return false;
        for (String suffix : suffixes) {
            if (suffix != null && str.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes diacritics (accents) from a string
     */
    public static String removeDiacritics(String str) {
        if (str == null)
            return null;
        return java.text.Normalizer.normalize(str, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Converts a string to a slug (URL-friendly)
     * Handles Vietnamese and other diacritics by converting to ASCII equivalents
     */
    public static String toSlug(String str) {
        if (isNullOrEmpty(str))
            return "";

        // First remove diacritics to convert accented characters to ASCII
        String normalized = removeDiacritics(str);

        // Convert to lowercase and replace spaces with hyphens
        String slug = normalized.toLowerCase().replaceAll("\\s+", "-")  // Replace spaces with hyphens
                .replaceAll("[^a-z0-9-]", "")  // Remove all non-alphanumeric chars except hyphens
                .replaceAll("-+", "-")  // Replace multiple hyphens with single hyphen
                .replaceAll("^-|-$", "");  // Remove leading/trailing hyphens

        return slug;
    }
}
