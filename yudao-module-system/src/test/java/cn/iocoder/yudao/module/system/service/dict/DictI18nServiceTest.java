package cn.iocoder.yudao.module.system.service.dict;

import cn.iocoder.yudao.framework.test.core.ut.BaseDbUnitTest;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictDataI18nDO;
import cn.iocoder.yudao.module.system.dal.dataobject.dict.DictTypeI18nDO;
import cn.iocoder.yudao.module.system.dal.mysql.dict.DictDataI18nMapper;
import cn.iocoder.yudao.module.system.dal.mysql.dict.DictTypeI18nMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 字典多语言服务测试
 *
 * @author 芋道源码
 */
@Import({DictI18nServiceImpl.class})
public class DictI18nServiceTest extends BaseDbUnitTest {

    @Resource
    private DictI18nService dictI18nService;

    @Resource
    private DictTypeI18nMapper dictTypeI18nMapper;
    @Resource
    private DictDataI18nMapper dictDataI18nMapper;

    @MockBean
    private DictTypeMapper dictTypeMapper;
    @MockBean
    private DictDataMapper dictDataMapper;

    @Test
    public void testCreateDictTypeI18n() {
        // 准备参数
        Long dictTypeId = 1L;
        String languageCode = "en_US";
        String name = "System Status";

        // 调用方法
        Long id = dictI18nService.createDictTypeI18n(dictTypeId, languageCode, name);

        // 断言
        assertNotNull(id);
        DictTypeI18nDO dictTypeI18n = dictTypeI18nMapper.selectById(id);
        assertNotNull(dictTypeI18n);
        assertEquals(dictTypeId, dictTypeI18n.getDictTypeId());
        assertEquals(languageCode, dictTypeI18n.getLanguageCode());
        assertEquals(name, dictTypeI18n.getName());
    }

    @Test
    public void testUpdateDictTypeI18n() {
        // 准备数据
        DictTypeI18nDO dictTypeI18n = DictTypeI18nDO.builder()
                .dictTypeId(1L)
                .languageCode("en_US")
                .name("Old Name")
                .build();
        dictTypeI18nMapper.insert(dictTypeI18n);

        // 调用方法
        dictI18nService.updateDictTypeI18n(1L, "en_US", "New Name");

        // 断言
        DictTypeI18nDO updated = dictTypeI18nMapper.selectById(dictTypeI18n.getId());
        assertEquals("New Name", updated.getName());
    }

    @Test
    public void testDeleteDictTypeI18nByDictTypeId() {
        // 准备数据
        DictTypeI18nDO dictTypeI18n1 = DictTypeI18nDO.builder()
                .dictTypeId(1L)
                .languageCode("en_US")
                .name("English Name")
                .build();
        DictTypeI18nDO dictTypeI18n2 = DictTypeI18nDO.builder()
                .dictTypeId(1L)
                .languageCode("ja_JP")
                .name("Japanese Name")
                .build();
        dictTypeI18nMapper.insert(dictTypeI18n1);
        dictTypeI18nMapper.insert(dictTypeI18n2);

        // 调用方法
        dictI18nService.deleteDictTypeI18nByDictTypeId(1L);

        // 断言
        List<DictTypeI18nDO> list = dictTypeI18nMapper.selectListByDictTypeId(1L);
        assertTrue(list.isEmpty());
    }

    @Test
    public void testCreateDictDataI18n() {
        // 准备参数
        Long dictDataId = 1L;
        String languageCode = "en_US";
        String label = "Enabled";

        // 调用方法
        Long id = dictI18nService.createDictDataI18n(dictDataId, languageCode, label);

        // 断言
        assertNotNull(id);
        DictDataI18nDO dictDataI18n = dictDataI18nMapper.selectById(id);
        assertNotNull(dictDataI18n);
        assertEquals(dictDataId, dictDataI18n.getDictDataId());
        assertEquals(languageCode, dictDataI18n.getLanguageCode());
        assertEquals(label, dictDataI18n.getLabel());
    }

    @Test
    public void testSaveDictTypeI18nBatch() {
        // 准备参数
        Long dictTypeId = 1L;
        Map<String, String> i18nTranslations = new HashMap<>();
        i18nTranslations.put("en_US", "System Status");
        i18nTranslations.put("ja_JP", "システム状態");
        i18nTranslations.put("ko_KR", "시스템 상태");

        // 调用方法
        dictI18nService.saveDictTypeI18nBatch(dictTypeId, i18nTranslations);

        // 断言
        List<DictTypeI18nDO> list = dictTypeI18nMapper.selectListByDictTypeId(dictTypeId);
        assertEquals(3, list.size());
        
        // 验证每个翻译
        for (DictTypeI18nDO item : list) {
            String expectedName = i18nTranslations.get(item.getLanguageCode());
            assertEquals(expectedName, item.getName());
        }
    }

    @Test
    public void testSaveDictDataI18nBatch() {
        // 准备参数
        Long dictDataId = 1L;
        Map<String, String> i18nTranslations = new HashMap<>();
        i18nTranslations.put("en_US", "Enabled");
        i18nTranslations.put("ja_JP", "有効");
        i18nTranslations.put("ko_KR", "활성화");

        // 调用方法
        dictI18nService.saveDictDataI18nBatch(dictDataId, i18nTranslations);

        // 断言
        List<DictDataI18nDO> list = dictDataI18nMapper.selectListByDictDataId(dictDataId);
        assertEquals(3, list.size());
        
        // 验证每个翻译
        for (DictDataI18nDO item : list) {
            String expectedLabel = i18nTranslations.get(item.getLanguageCode());
            assertEquals(expectedLabel, item.getLabel());
        }
    }

}