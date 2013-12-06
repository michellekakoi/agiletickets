package br.com.caelum.agiletickets.models;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

public class ReservaTest {
	@Test
	public void deveReservarIngressoSemDesconto(){
		WebDriver browser =  new FirefoxDriver();
		browser.navigate().to("http://localhost:8080/sessao/10");
		WebElement form = browser.findElement(By.id("reservaIngresso"));
		// guardando preco
		String precoUnitario = browser.findElement(By.id("preco")).getText().replace("Preco: ", "");

		form.findElement(By.id("qtde")).sendKeys("1");
		form.submit();
		
		WebElement msg = browser.findElement(By.id("message"));
		
		Assert.assertTrue(msg.getText().contains(precoUnitario));
	}

}