package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.JeopardyCategory;

import java.util.Objects;

public class JeopardyCategoryImpl implements JeopardyCategory {

    private String title;

    /**
     * Default constructor
     * @param title the title
     */
    public JeopardyCategoryImpl(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JeopardyCategoryImpl that = (JeopardyCategoryImpl) o;
        return Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
