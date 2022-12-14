package github.gold1100.schoolSystem.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private Long entityId;

    public EntityNotFoundException(Long entityId) {
        this.entityId = entityId;
    }

    public EntityNotFoundException(String message, Long entityId) {
        super(message);
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }

}
