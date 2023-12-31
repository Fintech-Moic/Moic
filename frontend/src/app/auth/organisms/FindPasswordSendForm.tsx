import InputForm from '../molecules/InputForm';
import FillButton from '@/components/atoms/FillButton';
import { ReactHookFormType } from '@/types/auth';
import { idPattern, emailPattern } from '@/util/validation';

interface FindPasswordSendFormProps extends ReactHookFormType {}

/**
 * @returns {JSX.Element} FindPasswordSendForm Component 반환
 */

export default function FindPasswordSendForm({
  register,
  errors,
  onSubmit,
}: FindPasswordSendFormProps) {
  return (
    <form
      onSubmit={onSubmit}
      className="w-full h-1/2 flex flex-col justify-evenly"
    >
      <InputForm
        register={register}
        id="name"
        name="name"
        type="text"
        placeholder="이름"
        isError={Boolean(errors.name)}
        notice={errors.name?.message}
        width="w-80"
        height="h-12"
      />
      <InputForm
        register={register}
        validation={idPattern}
        id="id"
        name="id"
        type="text"
        placeholder="아이디"
        isError={Boolean(errors.id)}
        notice="6자 이상 ~ 12자 이하, 영문(소문자), 숫자의 조합을 입력해주세요."
        width="w-80"
        height="h-12"
      />
      <div className="flex w-full justify-between">
        <InputForm
          register={register}
          validation={emailPattern}
          id="email"
          name="email"
          type="text"
          placeholder="이메일"
          isError={Boolean(errors.email)}
          notice="올바른 이메일을 입력해주세요."
          width="w-54"
          height="h-12"
        />
        <FillButton
          bgColor="bg-g4"
          height="h-12"
          width="w-20"
          type="submit"
          font="p2r"
          title="인증요청"
        />
      </div>
    </form>
  );
}
