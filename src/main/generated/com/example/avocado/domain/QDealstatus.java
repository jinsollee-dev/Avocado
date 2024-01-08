package com.example.avocado.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDealstatus is a Querydsl query type for Dealstatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDealstatus extends EntityPathBase<Dealstatus> {

    private static final long serialVersionUID = 762137676L;

    public static final QDealstatus dealstatus = new QDealstatus("dealstatus");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath buyer = createString("buyer");

    public final NumberPath<Long> dno = createNumber("dno", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath pname = createString("pname");

    public final NumberPath<Long> pno = createNumber("pno", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath seller = createString("seller");

    public QDealstatus(String variable) {
        super(Dealstatus.class, forVariable(variable));
    }

    public QDealstatus(Path<? extends Dealstatus> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDealstatus(PathMetadata metadata) {
        super(Dealstatus.class, metadata);
    }

}

