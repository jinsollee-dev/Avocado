package com.example.avocado.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductImg is a Querydsl query type for ProductImg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductImg extends EntityPathBase<ProductImg> {

    private static final long serialVersionUID = 838400322L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductImg productImg = new QProductImg("productImg");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> fno = createNumber("fno", Long.class);

    public final StringPath imgUrl = createString("imgUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final QProduct product;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath repimgYn = createString("repimgYn");

    public final StringPath uuid = createString("uuid");

    public QProductImg(String variable) {
        this(ProductImg.class, forVariable(variable), INITS);
    }

    public QProductImg(Path<? extends ProductImg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductImg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductImg(PathMetadata metadata, PathInits inits) {
        this(ProductImg.class, metadata, inits);
    }

    public QProductImg(Class<? extends ProductImg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

