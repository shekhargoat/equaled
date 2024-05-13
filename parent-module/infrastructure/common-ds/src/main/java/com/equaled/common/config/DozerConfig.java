/*
 *
 *  EqualEd, Inc. Confidential
 *
 *
 *  Copyright (c) EqualEd, Inc. 2024  2024. All rights reserved
 *
 *  NOTICE:  All information contained herein is, and remains
 *  the property of EqualEd, Inc. and its suppliers,
 *  if any.  The intellectual and technical concepts contained
 *  herein are proprietary to EqualEd, Inc.
 *  and its suppliers and may be covered by U.S. and Foreign Patents,
 *  patents in process, and are protected by trade secret or copyright law.
 *  Dissemination of this information or reproduction of this material
 *  is strictly forbidden unless prior written permission is obtained
 *  from EqualEd, Inc.
 *
 */
package com.equaled.common.config;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
@Configuration
public class DozerConfig {
  private static final Logger logger = LoggerFactory.getLogger(DozerConfig.class);
  private final DozerBeanMapper mapper;

  @Autowired
  public DozerConfig() {
    this.mapper = new DozerBeanMapper();
  }

  public void configureMapper(String... mappingFileUrls) {
    this.mapper.setMappingFiles(Arrays.asList(mappingFileUrls));
  }
}
