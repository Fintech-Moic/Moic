'use client';

/* eslint-disable react-hooks/exhaustive-deps */

import { useState, useEffect, useRef } from 'react';
import { FieldValues, useForm } from 'react-hook-form';
import { useRouter } from 'next/navigation';

import ProgressBar from '../atoms/ProgressBar';
import SignUpTextForm from '../organisms/SignUpTextForm';
import SignUpPersonalForm from '../organisms/SignUpPersonalForm';
import SignUpAccountForm from '../organisms/SignUpAccountForm';
import AuthSuccessForm from '../organisms/AuthSuccessForm';
import BothButtonGroup from '@/components/molecules/BothButtonGroup';
import { signUpApi } from '@/api/auth';
import TitleSentence from '@/components/atoms/TitleSentence';

export default function Page() {
  const {
    register,
    watch,
    handleSubmit,
    formState: { errors },
  } = useForm();
  const router = useRouter();
  const [percent, setPercent] = useState('w-0');
  const [step, setStep] = useState(0);
  const [userData, setUserData] = useState({});
  const [selectedData, setSelectedData] = useState<{
    gender: string | null;
    yearOfBirth: string | null;
  }>({ gender: null, yearOfBirth: null });
  const [fillButtonTitle, setFillButtonTitle] = useState('다음으로');
  const [title, setTitle] = useState('회원가입');
  const [sentence, setSentence] = useState('약관 동의하기');
  const genderList = ['선택안함', '남성', '여성'];
  const yearsList = [
    '선택안함',
    ...Array.from({ length: 124 }, (_, i) => (2023 - i).toString()),
  ];
  const dropDownData = {
    genderList,
    yearsList,
  };

  const personalFormRef = useRef<any>();
  const accountFormRef = useRef<any>();

  const forwardStep = () => {
    setStep(step + 1);
  };

  const backStep = () => {
    setStep(step - 1);
  };

  useEffect(() => {
    const settingProgress = () => {
      if (step === 0) {
        setPercent('w-0');
        setFillButtonTitle('다음으로');
        setTitle('회원가입');
        setSentence('약관 동의하기');
      } else if (step === 1) {
        setPercent('w-1/3');
        setFillButtonTitle('다음으로');
        setTitle('회원가입');
        setSentence('개인정보 입력');
      } else if (step === 2) {
        setPercent('w-2/3');
        setFillButtonTitle('회원가입 하기');
        setTitle('회원가입');
        setSentence('계정 정보 입력');
      } else if (step === 3) {
        setPercent('w-full');
        setTitle('회원가입');
        setSentence('가입 완료');
      }
    };
    settingProgress();
  }, [step]);

  const handleUserDataUpdate = (newData: FieldValues) => {
    setUserData({
      ...userData,
      ...selectedData,
      ...newData,
    });
  };

  useEffect(() => {
    const signUp = async () => {
      if (step === 2) {
        const result = await signUpApi(userData);
        if (result) {
          forwardStep();
        }
      } else if (step === 1) {
        forwardStep();
      }
    };
    signUp();
  }, [userData]);

  const onSubmit = handleSubmit((data) => {
    handleUserDataUpdate(data);
  });

  const nextStep = () => {
    if (step === 1 && personalFormRef.current) {
      personalFormRef.current.requestSubmit();
    } else if (step === 2 && accountFormRef.current) {
      accountFormRef.current.requestSubmit();
    } else if (step === 0) forwardStep();
  };

  const prevStep = () => {
    if (step > 0) backStep();
    else router.push('/auth/signIn');
  };
  const SignUpContentArr = [
    <SignUpTextForm>
      <div>
        <h1 className="h1b">개인정보 처리방침</h1>
        <br />
        <p className="p2r">
          핀프들(이하 &quot;회사&quot;라 함)은 개인정보보호법을 준수하며, 관련
          법령에 의거한 개인정보처리방침을 정하여 이용자 권익 보호에 최선을
          다하고 있습니다. 회사의 개인정보처리방침은 다음과 같은 내용을 담고
          있습니다.
        </p>
        <br />
        <h3 className="p2b"> 1. 개인정보의 처리 목적 및 수집 항목</h3>
      </div>
    </SignUpTextForm>,
    <SignUpPersonalForm
      ref={personalFormRef}
      register={register}
      errors={errors}
      watch={watch}
      dropDownData={dropDownData}
      onSubmit={onSubmit}
      setSelectedData={setSelectedData}
    />,
    <SignUpAccountForm
      ref={accountFormRef}
      register={register}
      errors={errors}
      watch={watch}
      onSubmit={onSubmit}
    />,
    <AuthSuccessForm buttonTitle="로그인하기" goingTo="auth/signIn">
      <section className="flex flex-col items-center">
        <div className="h3b flex">
          <h2 className="text-y4">모익</h2>
          <h2>에 오신걸 환영해요</h2>
        </div>
        <p className="p2r text-Primary">
          로그인 후, 다양한 서비스를 누려보세요!
        </p>
      </section>
    </AuthSuccessForm>,
  ];
  return (
    <div className="h-full flex flex-col">
      <div className="h-1/4 flex items-center">
        <TitleSentence title={title} sentence={sentence} />
      </div>
      <div className="h-3/4">
        <ProgressBar percent={percent} />
        <div className="h-2/3">{SignUpContentArr[step]}</div>
        {step < 3 && (
          <BothButtonGroup
            leftTitle="이전으로"
            onClickLeft={prevStep}
            rightTitle={fillButtonTitle}
            onClickRight={nextStep}
          />
        )}
      </div>
    </div>
  );
}
