package matpro29.library.Entity.api;

import lombok.Data;

@Data
public class FindTitle {
    private Result[] results;
    private String[] types;
}
