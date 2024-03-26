package com.equaled.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@AllArgsConstructor
@RestController
@Api(value = "EqualED API's")
@RequestMapping("/v1")
public class EqualEdController {


}
