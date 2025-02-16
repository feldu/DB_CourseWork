package test.coursework.utils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BaseUtils {
//    private final DSLContext dsl;
//    private final DishUtils dishUtils;
//    private final ProductUtils productUtils;
//    private final PrisonerUtils prisonerUtils;
//
//    public void clearData() {
//        dsl.transaction(configuration -> {
//            DSLContext ctx = DSL.using(configuration);
//            ctx.truncate(PRISONER_RATING).cascade().execute();
//            ctx.truncate(USER_ROLE).cascade().execute();
//            ctx.truncate(DISH_INGREDIENTS).cascade().execute();
//            ctx.truncate(PLATFORM_PRISONER).cascade().execute();
//            ctx.truncate(PRISONER).cascade().execute();
//            ctx.truncate(PLATFORM_USER).cascade().execute();
//            ctx.truncate(PLATFORM_ROLE).cascade().execute();
//            ctx.truncate(PRODUCT_WAREHOUSE).cascade().execute();
//            ctx.truncate(PRODUCT).cascade().execute();
//            ctx.truncate(DISH).cascade().execute();
//            ctx.truncate(PLATFORM_HISTORY).cascade().execute();
//            ctx.truncate(PLATFORM).cascade().execute();
//        });
//    }
//
//    public Dish createDish() {
//        Product product1 = productUtils.createProduct();
//        Product product2 = productUtils.createProduct();
//        return dishUtils.createDish(List.of(product1, product2));
//    }
//
//    public Prisoner createPrisoner() {
//        Dish dish = createDish();
//        return prisonerUtils.createPrisoner(dish.getId());
//    }
}
