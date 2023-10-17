import { UseFormRegister, FieldValues } from 'react-hook-form';

/**
 * @param {UseFormRegister<FieldValues>} register React-Hook-Form에서 값 등록에 사용되는 props
 * @param {Any} errors React-Hook-Form에서 error처리에 사용되는 props
 * @param {() => void} onSubmit React-Hook-Form에서 등록에 사용되는 함수
 * @param {String} watch ;
 */

export interface ReactHookFormType {
  register: UseFormRegister<FieldValues>;
  errors: any;
  onSubmit: () => void;
  watch?: string;
}
/** ProgressBar Component
 * @param {String} percent ProgressBar의 퍼센테이지, width를 이용해 작성
 * @returns {JSX.Element} ProgressBar Component 반환
 */
export interface ProgressBarProps {
  percent: string;
}

/** TwinsButtonGroup Component
 * @param {String} topTitle 위쪽 버튼의 title
 * @param {String} topBg 위쪽 버튼의 배경색
 * @param {React.MouseEventHandler<HTMLButtonElement>} onClicktop 위쪽 버튼의 callback 함수
 * @param {String} bottomTitle 아래쪽 버튼의 title
 * @param {String} bottomBg 아래쪽 버튼의 배경색
 * @param {React.MouseEventHandler<HTMLButtonElement>} onClickbottom 아래쪽 버튼의 callback 함수
 * @returns {JSX.Element} 위아래로 FillButton Component가 있는 TwinsButtonGroup Component
 */

export interface TwinsButtonGroupProps {
  height: string;
  topTitle: string;
  topBg: string;
  onClicktop: React.MouseEventHandler<HTMLButtonElement>;
  bottomTitle: string;
  bottomBg: string;
  onClickbottom: React.MouseEventHandler<HTMLButtonElement>;
}
