package Base;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class Helper {

     Page page;

    public Helper(Page page){ this.page = page; }


    public Locator getByLocator(String selector){
        return page.locator(selector);
    }

    public Locator getById(String selector){
        return page.locator("#"+selector);
    }

    public void navigate(String url) {
        page.navigate(url);
    }

    public void fill(Locator locator,String text){
        locator.fill(text);
    }

    public Locator getByText(String selector){
        return page.getByText(selector,new Page.GetByTextOptions().setExact(true));
    }


    public Locator getByTitle(String selector){
        return page.getByTitle(selector,new Page.GetByTitleOptions().setExact(true));
    }


    public Locator getByLabel(String selector){
        return page.getByLabel(selector,new Page.GetByLabelOptions().setExact(true));
    }

    public Locator getByPlaceholder(String selector){
        return page.getByPlaceholder(selector,new Page.GetByPlaceholderOptions().setExact(true));
    }

    public void wait(int millis){
        page.waitForTimeout(millis);
    }

    public void click(Locator locator){
        locator.click();
    }

    public String getInnerText(Locator locator){
        return locator.innerText();
    }


}