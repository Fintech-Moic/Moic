export const idPattern = {
  value: /^[a-z0-9]{6,12}$/,
  message: '아이디는 6~12자의 영문 소문자와 숫자 조합입니다.',
};
export const passwordPattern = {
  value:
    /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/,
  message: '비밀번호는 8~16자의 영문(대/소문자), 숫자, 특수문자 조합입니다.',
};

export const emailPattern = {
  value:
    /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
  message: '올바른 이메일을 입력해주세요',
};
