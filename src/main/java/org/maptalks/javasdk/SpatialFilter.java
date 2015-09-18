package org.maptalks.javasdk;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import org.maptalks.gis.core.geojson.Geometry;
import org.maptalks.gis.core.geojson.json.GeoJSONFactory;

/**
 * 空间查询条件类
 * @author fuzhen
 *
 */
public class SpatialFilter {
    // 图形关系常量定义
    //相交关系
    public final static int RELATION_INTERSECT = 0;
    //包含关系
    public final static int RELATION_CONTAIN = 1;
    //分离关系
    //  public final static int RELATION_DISJOINT = 2;
    //重叠关系
    public final static int RELATION_OVERLAP = 3;
    //相切关系
    public final static int RELATION_TOUCH = 4;
    //被包含关系
    public final static int RELATION_WITHIN = 5;
    //中心点包含关系
    public final static int RELATION_CONTAINCENTER = 7;

    /**
     * 空间关系比较的geometry对象
     */
    private Geometry geometry;
    /**
     * 空间关系，取值范围从上面定义的关系常量中取得
     */
    private int relation;

    public SpatialFilter() {

    }

    public SpatialFilter(Geometry geometry, int relation) {
        this.geometry = geometry;
        this.relation = relation;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setFilterGeometry(final Geometry geometry) {
        this.geometry = geometry;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(final int relation) {
        this.relation = relation;
    }

    /**
     * 解析json字符串生成SpatialFilter
     * @param json
     * @return
     */
    public static SpatialFilter create(String json) {
        SpatialFilter filter = JSON.parseObject(json, SpatialFilter.class, new ExtraProcessor() {

            public void processExtra(Object o, String s, Object value) {
                if ("geometry".equals(s)) {
                    String type = ((JSONObject) value).getString("type");
                    if (type != null) {
                        Class clazz = GeoJSONFactory.getGeoJsonType(type);
                        Geometry geo = (Geometry) JSON.toJavaObject(((JSONObject) value), clazz);
                        ((SpatialFilter) o).setFilterGeometry(geo);
                    }

                }
            }
        });
        return filter;
    }
}
