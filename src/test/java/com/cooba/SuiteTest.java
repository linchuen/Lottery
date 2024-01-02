package com.cooba;

import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("com.cooba")
@ExcludePackages("com.cooba.mapper")
public class SuiteTest {
}
