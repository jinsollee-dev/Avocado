package com.example.avocado.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReplyRoom is a Querydsl query type for ReplyRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReplyRoom extends EntityPathBase<ReplyRoom> {

    private static final long serialVersionUID = 1337863223L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReplyRoom replyRoom = new QReplyRoom("replyRoom");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final NumberPath<Long> rid = createNumber("rid", Long.class);

    public final QUser user;

    public QReplyRoom(String variable) {
        this(ReplyRoom.class, forVariable(variable), INITS);
    }

    public QReplyRoom(Path<? extends ReplyRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReplyRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReplyRoom(PathMetadata metadata, PathInits inits) {
        this(ReplyRoom.class, metadata, inits);
    }

    public QReplyRoom(Class<? extends ReplyRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

