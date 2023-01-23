package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import hello.hellospring.service.MemberService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/members")
public class MemberController {
	private final MemberService memberService;
	
	//얘는 컴포넌트라 단일 선언 해줘야함
	@Autowired
	public MemberController(MemberService memberService) {
	this.memberService = memberService;
	}

	@GetMapping("/new")
	public String creatForm(){
		return "members/createMemberForm";
	}

	@PostMapping("/new")
	public String create(MemberForm form){
		Member member = new Member();
		member.setName(form.getName());
		System.out.println("form : " + form);
		System.out.println("form : " + form.getName());
		memberService.join(member);

		return "redirect:/";
	}

	@GetMapping("")
	public String list(Model model) {
		List<Member> members = memberService.findMembers();
		model.addAttribute("members", members);
		return "members/memberList";
	}
}
