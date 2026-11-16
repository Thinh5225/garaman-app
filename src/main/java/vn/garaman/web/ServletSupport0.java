package vn.garaman.web;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

final class ServletSupport0 {
    private ServletSupport0() { }

    static Optional<Integer> positiveInt(String value) {
        try {
            int number = Integer.parseInt(value);
            return number > 0 ? Optional.of(number) : Optional.empty();
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    static Optional<LocalDate> date(String value) {
        if (value == null || value.isBlank()) return Optional.empty();
        try {
            return Optional.of(LocalDate.parse(value));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    static String url(HttpServletRequest request, String path) {
        return request.getContextPath() + path;
    }
}
