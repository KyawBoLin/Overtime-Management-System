// Scroll Effect

$(document).ready(function () {
    $("a.smooth-scroll").click(function (event) {
      event.preventDefault(); // close button event default
      var select_id = $(this).attr("href");
      $("html,body").animate(
        {
          scrollTop: $(select_id).offset().top - 64,
        },
        1300,
        "easeInOutExpo"
      );
    });
  });

// pre loader
$(window).on("load", function () {
  $("#status").fadeOut(2000);
  $("#preloader").delay(1100).fadeOut(1100);
});

$(window).on("load", function () {
  $("#status2").fadeOut(2000);
  $("#preloader2").delay(300).fadeOut(1100);
});

// modal

// Team Insert modal update

$(document).ready(function(){
	$("a.teamEdit").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(team,status){
			$('.myFormTeam #teamId').val(team.teamId);
			$('.myFormTeam #teamName').val(team.teamName);
			$('.myFormTeam #teamCheck').val(0);
		})
		
		jQuery.noConflict();
		$("#updateformTeam").modal();
	});
})

// Team Insert modal delete

$(document).ready(function(){
	$("a.teamDelete").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(team,status){
			$('.myDelFormTeam #teamId').val(team.teamId);
			$('.myDelFormTeam #teamName').val(team.teamName);
			$('.myDelFormTeam #teamCheck').val(1);
		})
		
		jQuery.noConflict();
		$("#deleteformTeam").modal();
	});
});


// Project Insert modal update
$(document).ready(function(){
	$("a.projectEdit").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(project,status){
			$('.myFormProject #projectId').val(project.projectId);
			$('.myFormProject #projectCode').val(project.projectCode);
			$('.myFormProject #projectCheck').val(0);
		})
		
		jQuery.noConflict();
		$("#updateformProject").modal();
	});
});

// Project Insert modal delete
$(document).ready(function(){
	$("a.projectDelete").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(project,status){
			$('.myDelFormProject #projectId').val(project.projectId);
			$('.myDelFormProject #projectCode').val(project.projectCode);
			$('.myDelFormProject #projectCheck').val(1);
		});
		
		jQuery.noConflict();
		$("#deleteformProject").modal();
	});
});

// Position Insert modal update
$(document).ready(function(){
	$("a.positionEdit").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(position,status){
			$('.myFormPosition #positionId').val(position.positionId);
			$('.myFormPosition #positionName').val(position.positionName);
			$('.myFormPosition #inputGroupSelect01').val(position.status);
			$('.myFormPosition #positionCheck').val(0);
		})
		
		jQuery.noConflict();
		$("#updateformPosition").modal();
	});
});

// Position Insert modal delete
$(document).ready(function(){
	$("a.positionDelete").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(position,status){
			$('.myDelFormPosition #positionId').val(position.positionId);
			$('.myDelFormPosition #positionName').val(position.positionName);
			$('.myDelFormPosition #inputGroupSelect01').val(position.status);
			$('.myDelFormPosition #positionCheck').val(1);
		});
		
		jQuery.noConflict();
		$("#deleteformPosition").modal();
	});
});

// OT rule Insert modal update
$(document).ready(function(){
	$("a.ruleEdit").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(rule,status){
			$('.myFormRule #ruleId').val(rule.ruleId);
			$('.myFormRule #ruleName').val(rule.ruleName);
			$('.myFormRule #ruleCheck').val(0);
		});
		
		jQuery.noConflict();
		$("#updateformRule").modal();
	});
});

// OT rule Insert modal delete
$(document).ready(function(){
	$("a.ruleDelete").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(rule,status){
			$('.myDelFormRule #ruleId').val(rule.ruleId);
			$('.myDelFormRule #ruleName').val(rule.ruleName);
			$('.myDelFormRule #ruleCheck').val(1);
		});
		
		jQuery.noConflict();
		$("#deleteformRule").modal();
	});
});

//MNG Insert modal update
$(document).ready(function(){
	$("a.manageEdit").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(manage,status){
			$('.myManagement #id').val(manage.id);
			$('.myManagement #teamStructureId').val(manage.teamStructureId);
			$('.myManagement #name').val(manage.name);
			$('.myManagement #staffId').val(manage.staffId);
			$('.myManagement #projectCode').val(manage.projectCode);
			$('.myManagement #teamName').val(manage.teamName);
			$('.myManagement #positionName').val(manage.positionName);
			$('.myManagement #salary').val(manage.salary);
		})
		
		jQuery.noConflict();
		$("#updateformManage").modal();
	});
	
//MNG Insert modal update
	$("a.manageDelete").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(manage,status){
			$('.myDelFormManagement #id').val(manage.id);
			$('.myDelFormManagement #teamStructureId').val(manage.teamStructureId);
			$('.myDelFormManagement #name').val(manage.name);
			$('.myDelFormManagement #projectCode').val(manage.projectCode);
			$('.myDelFormManagement #staffId').val(manage.staffId);
			$('.myDelFormManagement #teamName').val(manage.teamName);
			$('.myDelFormManagement #positionName').val(manage.positionName);
			$('.myDelFormManagement #salary').val(manage.salary);
		});
		
		jQuery.noConflict();
		$("#deleteformManagement").modal();
	})
});

//TM Insert modal update
$(document).ready(function(){
	$("a.memberEdit").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(member,status){
			$('.myTeamMember #memberId').val(member.memberId);
			$('.myTeamMember #teamStructureId').val(member.teamStructureId);
			$('.myTeamMember #memberName').val(member.memberName);
			$('.myTeamMember #memberStaffId').val(member.memberStaffId);
			$('.myTeamMember #memberProjectCode').val(member.memberProjectCode);
			$('.myTeamMember #memberTeamName').val(member.memberTeamName);
			$('.myTeamMember #memberPositionName').val(member.memberPositionName);
			$('.myTeamMember #memberSalary').val(member.memberSalary);
		})
		
		jQuery.noConflict();
		$("#updateformTeamMember").modal();
	});
	
//MNG Insert modal update
	$("a.memberDelete").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(member,status){
			$('.myDelFormTeamMember #memberId').val(member.memberId);
			$('.myDelFormTeamMember #teamStructureId').val(member.teamStructureId);
			$('.myDelFormTeamMember #memberName').val(member.memberName);
			$('.myDelFormTeamMember #memberStaffId').val(member.memberStaffId);
			$('.myDelFormTeamMember #memberProjectCode').val(member.memberProjectCode);
			$('.myDelFormTeamMember #memberTeamName').val(member.memberTeamName);
			$('.myDelFormTeamMember #memberPositionName').val(member.memberPositionName);
			$('.myDelFormTeamMember #memberSalary').val(member.memberSalary);
		});
		
		jQuery.noConflict();
		$("#deleteformTeamMember").modal();
	})
});

//Approval form modal update
$(document).ready(function(){
	$("a.approve").click(function(event){
		event.preventDefault();
		
		var href=$(this).attr('href');
		$.get(href,function(formInfo,status){
			$('.myApprove #id').val(formInfo.id);
		})
		
		jQuery.noConflict();
		$("#approvalform").modal();
	});
});
