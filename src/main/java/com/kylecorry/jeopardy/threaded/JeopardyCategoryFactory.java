package com.kylecorry.jeopardy.threaded;

import com.kylecorry.jeopardy.JeopardyCategory;

public class JeopardyCategoryFactory {
    private JeopardyCategoryFactory(){}

    /**
     * Construct a Jeopardy category
     * @param title the title
     * @return the JeopardyCategory
     */
    public static JeopardyCategory makeCategory(String title){
        return new JeopardyCategoryImpl(title);
    }
}
