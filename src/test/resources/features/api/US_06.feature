Feature: Bir yönetici olarak API baglantisi üzeriden sisteme kayitli visitor purpose bilgilerini guncelleyebilmeliyim.

  Scenario: US_06_01

    Given Api kullanicisi "api/visitorsPurposeAdd" path parametreleri set eder
    Then Api kullanicisi visitors_purpose, description bilgileriyle yeni bir visitor purpose kaydi olusturur
    And Api kullanicisi visitorsPurposeAdd donen status code in 200 oldugu ve response body deki message bilgisinin Success oldugu dogrulanmali

  Scenario: US_06_02

    Given Api kullanicisi "api/visitorsPurposeAdd" path parametreleri set eder
    Then Api kullanicisi visitorsPurposeAdd gecersiz authorization bilgileri ile gecerli data gonderir ve donen status code in 403 oldugu ve response body deki message bilgisinin Failed oldugu dogrulanmali
    Then Api kullanicisi visitorsPurposeAdd gecerli authorization bilgileri ile gecersiz data gonderir ve donen status code in 403 oldugu ve response body deki message bilgisinin Failed oldugu dogrulanmali

  Scenario: US_06_03
    Given Api kullanicisi "api/visitorsPurposeList" path parametreleri set eder
    Then Api kullanicisi visitorsPurposeList datalari ceker
    And Api kullanicisi ekledigi purpose un visitorsPurposeList te oldugunu dogrular

