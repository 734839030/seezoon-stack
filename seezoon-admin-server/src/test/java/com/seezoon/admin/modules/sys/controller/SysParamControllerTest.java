package com.seezoon.admin.modules.sys.controller;

import static com.seezoon.framework.api.DefaultCodeMsgBundle.SUCCESS;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.seezoon.admin.BaseSpringBootTest;

/**
 * @author hdf
 */
public class SysParamControllerTest extends BaseSpringBootTest {

    @Test
    public void queryById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sys/queryById").queryParam("id", "1")).andExpect(status().isOk())
            .andDo(print()).andExpect(jsonPath("$.code").value(SUCCESS.code()));
    }

    @Test
    void queryByPage() {}

    @Test
    void save() {}

    @Test
    void update() {}

    @Test
    void delete() {}
}