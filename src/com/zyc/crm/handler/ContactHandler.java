package com.zyc.crm.handler;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zyc.crm.bean.Contact;
import com.zyc.crm.bean.Customer;
import com.zyc.crm.model.Page;
import com.zyc.crm.service.ContactService;
import com.zyc.crm.service.CustomerServiceImpl;

@Controller
public class ContactHandler {

	@Autowired
	private ContactService contactService;
	
	@Autowired
	private CustomerServiceImpl customerServiceImpl;

	// 删除联系人
	//如果当前客户只有一个联系人则不能删除
	@RequestMapping("/contact/delete/{contactId}/{customerId}")
	public String delete(@PathVariable("contactId") Integer contactId,@PathVariable("customerId") Integer customerId,RedirectAttributes redirectAttributes){
		
		
		Customer customer = customerServiceImpl.getCustomerById(customerId);
		if(customer.getContacts().size()==1){
			redirectAttributes.addFlashAttribute("message", "不能删除唯一的联系人");
			return "redirect:/contact/list/"+customerId;
		}
		
		contactService.delete(contactId);
		redirectAttributes.addFlashAttribute("message", "删除成功");
		return "redirect:/contact/list/"+customerId;
		
	}

	// 更新联系人
	// 这个id是当前联系人的id
	@RequestMapping("/contact/toUpdateUI/{id}")
	public String toUpdateUI(@PathVariable("id") Integer id,
			Map<String, Object> map) {

		Contact contact = contactService.getContactById(id);
		map.put("contact", contact);

		return "contact/input";
	}

	// 添加或者更新联系人
	@RequestMapping("/contact/saveOrupdate")
	public String saveOrUpdateContact(Contact contact) {

		// 如果有id则更新
		if (contact.getId() != null) {
			contactService.update(contact);
			return "redirect:/contact/list/" + contact.getCustomer().getId();
		}

		contactService.save(contact);

		return "redirect:/contact/list/" + contact.getCustomer().getId();
	}

	// 跳转到编辑页面
	// 参数中的id为customerId
	@RequestMapping("/contact/create/{id}")
	public String toSaveUI(Map<String, Object> map,
			@PathVariable("id") Integer id) {

		/**
		 * 跳转到添加页面需要一个customer的id ，因为添加到以后 跳转到联系人页面上 需要一个当前customer的id
		 */
		Contact contact = new Contact();
		Customer customer = new Customer();
		long cid = (long) id;
		customer.setId(cid);
		contact.setCustomer(customer);
		map.put("contact", contact);

		return "contact/input";
	}

	@RequestMapping("/contact/list/{id}")
	public String showList(
			@RequestParam(value = "pageNo", required = false) String pageNoStr,
			@PathVariable("id") Integer id, Map<String, Object> map) {

		Page<Contact> page = contactService.getPage(pageNoStr, id);
		map.put("page", page);

		Customer manager = contactService.getManager(id);
		map.put("customer", manager);

		return "contact/list";
	}
}
