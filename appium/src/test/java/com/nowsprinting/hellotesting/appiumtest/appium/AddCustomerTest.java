    /*
 * Copyright 2015 TOYAMA Sumio
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nowsprinting.hellotesting.appiumtest.appium;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.nowsprinting.hellotesting.appiumtest.appium.page.DetailPage;
import com.nowsprinting.hellotesting.appiumtest.appium.page.Gender;
import com.nowsprinting.hellotesting.appiumtest.appium.page.MasterPage;
import com.nowsprinting.hellotesting.appiumtest.appium.page.PreviewPage;

/**
 * Appiumモードで動作するテストクラスのサンプル。
 * テストケースにWebViewの操作が含まれているため、試験対象のプラットフォームがAndroid 4.4以上でないと動作しない。
 */

public class AddCustomerTest {

    private AndroidDriver mDriver;

    @Before
    public void setUp() throws Exception {
        AppiumLauncher.launch();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.nowsprinting.hellotesting.appium");
        capabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, "com.nowsprinting.hellotesting.app.CustomerListActivity");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator");
        capabilities.setCapability("unicodeKeyboard", true);
        capabilities.setCapability("resetKeyboard", true);
        capabilities.setCapability("app", "C:\\AndroidStudioProjects\\AndroidAppsTestAutomationSamples\\app\\build\\outputs\\apk\\app-appium-debug.apk");
        mDriver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        mDriver.manage().timeouts().implicitlyWait(80, TimeUnit.SECONDS);
    }

    @After
    public void tearDown() throws Exception {
        mDriver.quit();

        AppiumLauncher.stop();
    }

    /**
     * 新規に顧客を追加し、プレビュー画面に追加した顧客の情報が正しく表示されることを確認する。
     * また、プレビュー画面上に表示される階層が正しいこともあわせて確認する。
     */
    @Test
    public void 新規顧客が追加できること() throws Exception {
        MasterPage master = new MasterPage(mDriver);
        DetailPage detail = master.goDetailPageToAddCustomer();
        PreviewPage preview = detail.inputName("izuishi youichi")
                .inputMailAddress("yizuishi@iti.co.jp")
                .selectGender(Gender.FEMALE)
                .saveAndPreview();
//                .inputAge(9)

        assertThat(preview.getName(), is("izuishi youichi"));
        assertThat(preview.getMailAddress(), is("yizuishi@iti.co.jp"));
//        assertThat(preview.getAge(), is("9"));
        assertThat(preview.getGender(), is("女性"));
//        assertThat(preview.getDivision(), is("C層"));
    }

    @Test
    public void 新規顧客が追加できること2() throws Exception {
        MasterPage master = new MasterPage(mDriver);
        DetailPage detail = master.goDetailPageToAddCustomer();
        PreviewPage preview = detail.inputName("test taro")
                .inputMailAddress("test@iti.co.jp")
                .selectGender(Gender.MALE)
                .saveAndPreview();
//                .inputAge(15)

        assertThat(preview.getName(), is("test taro"));
        assertThat(preview.getMailAddress(), is("test@iti.co.jp"));
//        assertThat(preview.getAge(), is("15"));
        assertThat(preview.getGender(), is("男性"));
//        assertThat(preview.getDivision(), is("T層"));
    }
}
