

Feature: Bir yönetici olarak API baglantisi üzerinden id girerek ilgili kan verilerine erisebilmeliyim.

  Scenario: TC_01 /api/getBloodGroupById endpoint'ine gecerli authorization bilgileri ve dogru data (id) iceren bir GET body gönderildiginde dönen status code'in 200 oldugu ve response body'deki message bilgisinin "Success" oldugu dogrulanmali
    Given Api kullanicisi "api/getBloodGroupById" path parametreleri set eder
    Then Api kullanicisi "BloodGroupById" icin gonderdigi dogru id iceren bir GET body sonucunda dönen status code in ikiyuz oldugunu dogrular ve response body deki message bilgisinin Success oldugu dogrulanmali


  Scenario: TC_02 /api/getBloodGroupById endpoint'ine gecersiz authorization bilgileri ve gecersiz data (id) iceren bir GET body gönderildiginde dönen status code'in 403 oldugu ve response body'deki message bilgisinin "failed" oldugu dogrulanmali
    Given Api kullanicisi "api/getBloodGroupById" path parametreleri set eder
    Then Api kullanicisi "BloodGroupById" icin gonderdigi gecersiz id iceren bir GET body gönderildiginde dönen status codein 403 oldugu ve response bodydeki message bilgisinin "failed" oldugu dogrulanmali
  @nesli
  Scenario: TC_03 Response body icindeki datalar(id, name, is_blood_group, created_at) dogrulanmali.
    Given Api kullanicisi "api/getBloodGroupById" path parametreleri set eder
    Then Api kullanicisi response body icindeki "4" "AB+" "1" "2021-10-25 02:32:48" datalarini dogrular



