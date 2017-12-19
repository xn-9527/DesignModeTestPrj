package test;

/**
 * Created by xiaoni on 2017/3/15.
 */
public class Student {
    private Long id;
    private String name;

    public Student(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
