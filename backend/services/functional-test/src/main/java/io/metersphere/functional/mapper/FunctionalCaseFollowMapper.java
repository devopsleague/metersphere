/**
 * @filename:FunctionalCaseFollowDao 2023年5月17日
 * @project ms  V3.x
 * Copyright(c) 2020 wx Co. Ltd. 
 * All right reserved. 
 */
package io.metersphere.functional.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import io.metersphere.functional.domain.FunctionalCaseFollow;


@Mapper
public interface FunctionalCaseFollowMapper extends BaseMapper<FunctionalCaseFollow> {
	
}