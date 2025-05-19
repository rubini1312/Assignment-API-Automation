package model;

import java.util.List;

public class Pet {
    public long id;
    public String name;
    public String status;
    public Category category;
    public List<String> photoUrls;

    public static class Category {
        public long id;
        public String name;
    }
}
