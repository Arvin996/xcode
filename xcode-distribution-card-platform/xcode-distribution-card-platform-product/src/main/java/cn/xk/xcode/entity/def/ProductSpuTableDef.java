package cn.xk.xcode.entity.def;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;


/**
 * 表定义层。
 *
 * @author xuk
 * @since 2025-05-30
 */
public class ProductSpuTableDef extends TableDef {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final ProductSpuTableDef PRODUCT_SPU_PO = new ProductSpuTableDef();

    /**
     * 商品 SPU 编号，自增
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 商品名称
     */
    public final QueryColumn NAME = new QueryColumn(this, "name");

    /**
     * 排序字段
     */
    public final QueryColumn SORT = new QueryColumn(this, "sort");

    /**
     * 商品关键词 多个用逗号隔开
     */
    public final QueryColumn KEYWORDS = new QueryColumn(this, "keywords");

    /**
     * 商品封面图
     */
    public final QueryColumn PIC_URL = new QueryColumn(this, "pic_url");

    /**
     * 0 上架 1下架
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 商品所属店铺
     */
    public final QueryColumn STORE_ID = new QueryColumn(this, "store_id");

    /**
     * 商品视频
     */
    public final QueryColumn VIDEO_URL = new QueryColumn(this, "video_url");

    /**
     * 是否删除 0未删除 1已删除
     */
    public final QueryColumn IS_DELETED = new QueryColumn(this, "is_deleted");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 创建人
     */
    public final QueryColumn CREATE_USER = new QueryColumn(this, "create_user");

    /**
     * 商品销量
     */
    public final QueryColumn SALES_COUNT = new QueryColumn(this, "sales_count");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 更新人
     */
    public final QueryColumn UPDATE_USER = new QueryColumn(this, "update_user");

    /**
     * 审核状态 00 待审核 10审核通过 20审核不通过
     */
    public final QueryColumn AUDIT_STATUS = new QueryColumn(this, "audit_status");

    /**
     * 商品详情
     */
    public final QueryColumn DESCRIPTION = new QueryColumn(this, "description");

    /**
     * 商品简介
     */
    public final QueryColumn INTRODUCTION = new QueryColumn(this, "introduction");

    /**
     * 是否热卖推荐: 0 默认 1 热卖
     */
    public final QueryColumn RECOMMEND_HOT = new QueryColumn(this, "recommend_hot");

    /**
     * 是否新品推荐: 0 默认 1 新品
     */
    public final QueryColumn RECOMMEND_NEW = new QueryColumn(this, "recommend_new");

    /**
     * 是否精品推荐: 0 默认 1 精品
     */
    public final QueryColumn RECOMMEND_BEST = new QueryColumn(this, "recommend_best");

    /**
     * 是否优品推荐
     */
    public final QueryColumn RECOMMEND_GOOD = new QueryColumn(this, "recommend_good");

    /**
     * 商品轮播图地址
     * 数组，以逗号分隔
     * 最多上传15张
     */
    public final QueryColumn SLIDER_PIC_URLS = new QueryColumn(this, "slider_pic_urls");

    /**
     * 是否优惠推荐: 0 默认 1 优选
     */
    public final QueryColumn RECOMMEND_BENEFIT = new QueryColumn(this, "recommend_benefit");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, STORE_ID, NAME, INTRODUCTION, DESCRIPTION, KEYWORDS, PIC_URL, SLIDER_PIC_URLS, VIDEO_URL, SORT, AUDIT_STATUS, STATUS, RECOMMEND_HOT, RECOMMEND_BENEFIT, RECOMMEND_BEST, RECOMMEND_NEW, RECOMMEND_GOOD, SALES_COUNT, IS_DELETED, CREATE_TIME, CREATE_USER, UPDATE_TIME, UPDATE_USER};

    public ProductSpuTableDef() {
        super("", "product_spu");
    }

    private ProductSpuTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public ProductSpuTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new ProductSpuTableDef("", "product_spu", alias));
    }

}
