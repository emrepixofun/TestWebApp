package com.emre.konumnot.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLocationNote is a Querydsl query type for LocationNote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLocationNote extends EntityPathBase<LocationNote> {

    private static final long serialVersionUID = -1727976093L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLocationNote locationNote = new QLocationNote("locationNote");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ComparablePath<org.locationtech.jts.geom.Point> location = createComparable("location", org.locationtech.jts.geom.Point.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final QUser user;

    public QLocationNote(String variable) {
        this(LocationNote.class, forVariable(variable), INITS);
    }

    public QLocationNote(Path<? extends LocationNote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLocationNote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLocationNote(PathMetadata metadata, PathInits inits) {
        this(LocationNote.class, metadata, inits);
    }

    public QLocationNote(Class<? extends LocationNote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

