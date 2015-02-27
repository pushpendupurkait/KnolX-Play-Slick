package controllers

import play.api._
import play.api.db.DB
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.db.slick._
import play.api.Play.current
import play.api.data.format.Formats._
import play.api.db.slick.Config.driver.simple._
import play.api.data.Form
import play.api.db.slick.DBAction
import play.twirl.api.Html
import views._
import models._
import play.mvc.Results.Redirect
import scala.slick.lifted.TableQuery
import play.mvc.Results.Redirect
import java.util.Date
import play.mvc.Results.Redirect
import play.mvc.Results.Redirect

object Application extends Controller {

  
  /**
   * In this project there is some functionality if user is logged in then it 
   * redirects him/her to dashboard otherwise user is redirected to Sign-In Page
   */
  /**
   * Index for index page
   */
  def index = Action { request =>
    request.session.get("EmailID").map { Email =>
      Ok(views.html.dashboard(Email))
    }.getOrElse {
      Ok(views.html.signInForm(signInForm))
      //Redirect(routes.Application.signInPage()).flashing("failure" ->"No record Deleted.")
    }
  }
  
  /**
   * signUpPage is for Sign Up Form
   */
  
  def signUpPage = Action { request =>
    request.session.get("EmailID").map { Email =>
      Ok(views.html.dashboard(Email))
    }.getOrElse {
      Ok(views.html.signUpForm(signUpForm))
    }
  }
  /**
   * signInPage action has functionality if user is logged in then it 
   * redirects him to dashboard otherwise user is redirected to Sign-In Page
   */
  
  def signInPage = Action { request =>
    request.session.get("EmailID").map { Email =>
      Ok(views.html.dashboard(Email))
    }.getOrElse {
      Ok(views.html.signInForm(signInForm))
    }
  }
  /**
   * contact action takes user to contact us page with has Google Map 
   * which shows location of Knoldus softwares LLP.
   */
  
  def contact = Action{ request =>
    request.session.get("EmailID").map { Email =>
      Ok(views.html.contact("Contact"))
    }.getOrElse {
      Ok(views.html.signInForm(signInForm))
    }
  }
  /**
   * When user is Logged-in then Logout action helps him/her
   * to logout, It ends the Session
   */
  
  def logout = Action { request =>
    Ok(views.html.signInForm(signInForm)).withNewSession
  }
  /**
   * updatePage redirects user to Update Form, to make changes in information. 
   */
  
  def updatePage = DBAction { implicit request =>
    request.session.get("EmailID").map { Email =>
      val record=UserTable.getRecordByEmail(Email)
      Ok(views.html.updateForm(signUpForm.fill(record)))
    }.getOrElse {
      Ok(views.html.signInForm(signInForm))
    }
  }
  /**
   * It takes user to dashboard, when user in logged in.
   */
  
  def dashboard = Action {
    Ok(views.html.dashboard("Update"))
  }
  
  /**
   * signUp works for signUp functionality
   * It inserts data in table
   */

  def signUp: Action[AnyContent] = DBAction { implicit rs =>
    try {
      val success = signUpForm.bindFromRequest.get
      val afftdRow = UserTable.signUp(success)
      Ok(views.html.signInForm(signInForm))
    } catch {
      case ex: Exception =>
        Ok(views.html.signUpForm(signUpForm))
    }
  }
  
  /**
   * signIn works for signIn functionality
   */
  
  def signIn: Action[AnyContent] = DBAction { implicit rs =>
    try {
      val success = signInForm.bindFromRequest.get
      val count = UserTable.signIn(success)
      if (count > 0) {
        Ok(views.html.dashboard(success.Email)).withSession("EmailID" -> success.Email)
      } else {
        Ok("Logged In Not Done")
      }
    } catch {
      case ex: Exception =>
        Ok(views.html.signInForm(signInForm))
    }
  }
  
  /**
   * update works to make changes in users data
   * 
   */
  
  def update: Action[AnyContent] = DBAction { implicit rs =>

    val data = rs.session.get("EmailID").map {
      Email =>
        {
          val oldRecord = UserTable.getRecordByEmail(Email)
          val formData = signUpForm.bindFromRequest.get
          val dataToUpdate: User = formData.copy(id = oldRecord.id, Created = oldRecord.Created)
          val afftdRow = UserTable.update(dataToUpdate, Email)
          dataToUpdate
        }
    }
    Ok(views.html.dashboard(data.get.Email)).withSession(
      "EmailID" -> data.get.Email)
    
  }
  
  /**
   * showInfo works to show user's data on screen.
   */
  def showInfo: Action[AnyContent] = DBAction { implicit rs =>
    try {
      val data = rs.session.get("EmailID").map { Email =>
        UserTable.getRecordByEmail(Email)
      }.get
      Ok(views.html.showInformation(data))
    } catch {
      case ex: Exception =>
        Ok(views.html.signInForm(signInForm))
    }
  }

  /**
   * signUpForm is form to take data from user.
   */
  val signUpForm = Form(
    mapping(
      "Name" -> nonEmptyText,
      "Address" -> nonEmptyText,
      "Company" -> nonEmptyText,
      "Email" -> nonEmptyText,
      "Password" -> nonEmptyText,
      "UserType" -> ignored(2),
      "Phone" -> nonEmptyText,
      "id" -> ignored(1),
      "Created" -> ignored(new Date),
      "Updated" -> ignored(new Date))(User.apply)(User.unapply))

      
  /**
   * signInForm takes Email and Password from user to sign In
   */
  val signInForm = Form(
    mapping(
      "Email" -> nonEmptyText,
      "Password" -> nonEmptyText)(SignIn.apply)(SignIn.unapply))

}
/**
 * case classes for forms
 */
case class SignIn(Email: String, Password: String)
case class User(Name: String, Address: String, Company: String, Email: String, Password: String, UserType: Int, Phone: String, id: Int, Created: Date, Update: Date)
