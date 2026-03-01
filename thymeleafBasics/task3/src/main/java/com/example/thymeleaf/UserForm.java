package com.example.thymeleaf;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ユーザー登録フォームのデータを保持するクラス
 * バリデーションルールをアノテーションで定義
 */
public class UserForm {

    @NotBlank(message = "名前を入力してください")
    @Size(max = 50, message = "名前は50文字以内で入力してください")
    private String name;

    @NotBlank(message = "メールアドレスを入力してください")
    @Email(message = "正しいメールアドレス形式で入力してください")
    private String email;

    @NotBlank(message = "部署を選択してください")
    private String department;

    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, message = "パスワードは8文字以上で入力してください")
    private String password;

    @NotBlank(message = "確認用パスワードを入力してください")
    private String passwordConfirm;

    private boolean agreedToTerms;

    @AssertTrue(message = "パスワードと確認用パスワードは同一にしてください。")
	public boolean isPasswordValid() {
		if (password == null || password.isEmpty()) {
			return true;
		}

		return password.equals(passwordConfirm);
	} 

    @AssertTrue(message = "利用規約に同意しない場合登録できません")
	public boolean isAgreedValid() {
		return agreedToTerms;
	} 
    

    // Getter/Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isAgreedToTerms() {
        return agreedToTerms;
    }

    public void setAgreedToTerms(boolean agreedToTerms) {
        this.agreedToTerms = agreedToTerms;
    }
}
