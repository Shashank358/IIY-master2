<?php  include("afterlogin.php");
$eid=cleanTAG($_POST['eid']);
$ev=cleanTAG($_POST['ev']);

if($eid!="")$bk='?id='.$eid;

			$_SESSION['stunm']=$stunm;
			$_SESSION['fnm']=$fnm;
			$_SESSION['mnm']=$mnm;
			
$err=0;
$errdt="<label>Error(s)</label>";
if(!$ev) {$errdt=$errdt."<br>Please Enter Subject"; $err=1;}

if($err==0) {
if($eid=="")
{	

	$dbh->exec("INSERT INTO stu_gallerycat(ALB) VALUES ('$ev')");
}
else
{
		$dbh->exec("UPDATE stu_gallerycat SET ALB='$ev' where ID=".$eid."");
		
}
			
			unset($_SESSION['em']);
			unset($_SESSION['rem']);
			unset($_SESSION['dep']);
		
			unset($_SESSION['form_error'])		;
			header("Location:addgallerycat.php");
			exit();
			
		} else {

			$_SESSION['form_error']	=$errdt;	
			unset($_SESSION['form_success'])	;	
			header("Location:addgallerycat.php$bk");
			exit();
		}
	
	
?>