package com.base.backend.utils.excel;

import com.base.backend.utils.excel.annotation.ExcelField;
import com.google.common.collect.Lists;

import java.lang.reflect.AccessibleObject;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public abstract class ImportExportBase {

    /**
     * @param type 字段类型（0：导出导入；1：仅导出；2：仅导入）
     * @return
     */
    public <E> List<Object[]> getAnnotationListAndSort(Class<E> cls, int type) {
        List<Object[]> annotationList = Lists.newArrayList();
        addExcelField(annotationList, cls.getDeclaredFields(), type);
        addExcelField(annotationList, cls.getDeclaredMethods(), type);
        Collections.sort(annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort()).compareTo(
                        new Integer(((ExcelField) o2[0]).sort()));
            }
        });
        return annotationList;
    }

    protected void addExcelField(List<Object[]> annotationList, AccessibleObject[] accessibleObjects, int type) {
        for (AccessibleObject ao : accessibleObjects) {
            ExcelField ef = ao.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                annotationList.add(new Object[]{ef, ao});
            }
        }
    }
}
