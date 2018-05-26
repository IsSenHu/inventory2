package ecjtu.husen.shiro;

import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashMap;

/**
 * 自定义FilterChainDefinition
 * @author husen
 */
public class FilterChainDefinitionMapBuilder {

    @Value("${shiro.anon}")
    private String anon;
    @Value("${shiro.authc}")
    private String authc;
    @Value("${shiro.basic.info.manage}")
    private String basicInfoManage;
    @Value("${shiro.Statistical.analysis.manage}")
    private String statisticalAndAnalysisManage;
    @Value("${shiro.deliver.manage}")
    private String deliverManage;
    @Value("${shiro.in.manage}")
    private String inManage;
    @Value("${shiro.goods.manage}")
    private String goodsManage;
    @Value("${shiro.console.mamage}")
    private String consoleManage;
    @Value("${shiro.out.manage}")
    private String outManage;
    @Value("${shiro.system.mamage}")
    private String systemManage;
    @Value("${shiro.note.mamage}")
    private String noteManage;

    public LinkedHashMap<String, String> builderFilterChainDefinitionMap(){
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        //ApplyerController
        map.put("/*Applyer**", basicInfoManage);
        //BrandController
        map.put("/*Brand**", basicInfoManage);
        //ChartController
        map.put("/lt**", statisticalAndAnalysisManage);
        //DeliverController
        map.put("/findDeliverOrder.action", deliverManage);
        map.put("/deliverGoods.action", deliverManage);
        map.put("/cancelDeliver.action", deliverManage);
        //InController
        map.put("/showAllSport.action", basicInfoManage);
        map.put("/*In**", inManage);
        map.put("/pageNoVerfy.action", inManage);
        map.put("/access.action", inManage);
        map.put("/faile.action", inManage);
        //ItemController
        map.put("/*Item**", goodsManage);
        map.put("/*Pic**", goodsManage);
        //ManagerController
        map.put("/*Manager**", consoleManage);
        map.put("/*Role**", consoleManage);
        //MaterialController
        map.put("/*Material**", basicInfoManage);
        //MessageController
        map.put("/wait**", authc);
        map.put("/warnStockNumber.action", authc);
        map.put("/count.action", authc);
        //OutController
        map.put("/*out**", outManage);
        map.put("/outAccess.action", outManage);
        map.put("/outFaile.action", outManage);
        //PermissionController
        map.put("/*Permission**", systemManage);
        map.put("/showUrls.action", systemManage);
        //RoleController
        map.put("/*Role**", systemManage);
        //SpecificationController
        map.put("/*Specification**", basicInfoManage);
        //SportController
        map.put("/*Sport**", basicInfoManage);
        //SportItemController
        map.put("/*SportItem**", basicInfoManage);
        //UrlController
        map.put("/*Url**", systemManage);
        //UserController
        map.put("/sendSms.action", anon);
        map.put("/userRegist.action", anon);
        map.put("/userLogin.action", anon);
        map.put("/pageUser.action", anon);
        map.put("/showUser.action", anon);
        map.put("/updateUser.action", anon);
        //VerfyNoteController
        map.put("/*Note**", noteManage);
        //ViewsController
        map.put("/toRegist.action", anon);
        map.put("/toLogin.action", anon);
        map.put("/toIndex.action", anon);
        //logout
        map.put("/shiro/logout", "logout");
        return map;
    }
}
