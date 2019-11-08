package ekb.sboot.ktpub.api.models;

import ru.bio4j.spring.common.model.Mapper;

public class Hint {
    private String id;
    @Mapper(name = "aname")
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
