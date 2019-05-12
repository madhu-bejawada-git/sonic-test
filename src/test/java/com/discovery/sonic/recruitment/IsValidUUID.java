package com.discovery.sonic.recruitment;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.util.UUID;

public class IsValidUUID extends TypeSafeMatcher<String> {

    @Override
    protected boolean matchesSafely(String s) {
        try {
            //noinspection ResultOfMethodCallIgnored
            UUID.fromString(s);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("valid UUID");
    }

    public static Matcher<String> validUUID() {
        return new IsValidUUID();
    }
}
