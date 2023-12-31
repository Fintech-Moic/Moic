/* eslint-disable @typescript-eslint/no-unused-vars */

'use client';

/* 카테고리 검색 페이지 구현 */

import React, { useState, useEffect, useCallback, ChangeEvent } from 'react';
import { useAtom } from 'jotai';
import { getShopData } from '@/api/map';
import searchResultAtom from '@/store/atoms/searchResultAtom';

/**
 * 검색어 입력 및 검색 결과 출력 함수
 */
export default function SimpleSearchBox() {
  const [inputValue, setInputValue] = useState('');
  const [searchResult, setSearchResult] = useAtom(searchResultAtom);
  let debounceTimer: string | number | NodeJS.Timeout | undefined;

  const autocompletedShopList = useCallback(
    (e: ChangeEvent<HTMLInputElement>) => {
      e.preventDefault();
      const newValue = e.currentTarget.value;
      setInputValue(newValue);
      clearTimeout(debounceTimer);
    },
    [debounceTimer]
  );

  /** 가맹점 데이터 API 호출 함수
   * @param {String | Number} value 검색어
   */
  const listOfShop = useCallback(
    async (value: string | number) => {
      const posts = await getShopData(value);
      setSearchResult(posts);
    },
    [setSearchResult]
  );

  /** Debounce -> 검색 타이머 초기화 -> 기존 검색어 요청 대기 상태 초기화
   */
  useEffect(() => {
    const delay = 500;
    const timerId = setTimeout(() => {
      if (inputValue) {
        listOfShop(inputValue);
      } else {
        setSearchResult([]);
      }
    }, delay);
    return () => clearTimeout(timerId);
  }, [inputValue, listOfShop, setSearchResult]);

  return (
    <input
      type="text"
      id="inputValue"
      name="inputValue"
      placeholder="내 카드 혜택, 여기서 쓰자!"
      onChange={autocompletedShopList}
      value={inputValue}
      className="relative font-suit text-xl ml-4 w-11/12 h-14 focus:outline-none"
    />
  );
}
