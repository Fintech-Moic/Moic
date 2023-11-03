/** DropdownItem의 type
 * @param {String} name Dropdown Item의 이름
 * @param {String} link 클릭 시 이동될 경로
 */

export interface DropdownItem {
  name: string;
  link: string;
}

/** HomeDropDownProps
 * @param {String} isOpen Dropdown Item의 이름
 * @param {String} items 클릭 시 이동될 경로
 * @param {() => void} onClick Callback 함수
 * @param {React.MouseEventHandler<HTMLButtonElement>} signOut
 * @returns {JSX.Element} HomeDropDown 컴포넌트
 */
export interface HomeDropDownProps {
  isOpen: boolean;
  items: DropdownItem[];
  onClick: () => void;
  signOut: React.MouseEventHandler<HTMLButtonElement>;
}

/** HomeNameProps
 * @param {String} name Home화면에서 출력될 이름
 * @returns {JSX.Element} 이름 출력
 */
export interface HomeNameProps {
  name: string;
}
/** HomeDDnNameProps
 * @param {React.RefObject<HTMLElement>} innerRef useRef에서 반환받는 RefObject
 * @returns {JSX.Element} 이름 출력
 */
export interface HomeDDnNameProps extends HomeDropDownProps, HomeNameProps {
  innerRef: React.RefObject<any>;
}

/** BoxItems Component
 * @param {String} title Title Sentence에 전달할 title
 * @param {String} sentence Title Sentence에 전달할 sentence
 * @param {String} imgSrc 이미지 주소
 * @param {String} going 이동 경로
 */
export interface BoxItems {
  going: string;
  imgSrc: string;
  title: string;
  sentence: string;
}
