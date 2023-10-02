'use client';

import { useState, useEffect } from 'react';
import ProfileContainer from '../organisms/ProfileContainer';
import Header from '@/components/molecules/Header';
import Navbar from '@/components/molecules/Navbar';
import { fetchProfile, updateProfile } from '@/api/myPage';

export default function Page() {
  const [profileData, setProfileData] = useState<{
    name: string;
    email: string;
    gender: string;
    yearOfBirth: string;
  } | null>(null);
  const [selectedData, setSelectedData] = useState<{
    gender: string | null;
    yearOfBirth: string | null;
  }>({ gender: null, yearOfBirth: null });
  const genderList = ['선택안함', '남성', '여성'];
  const yearsList = [
    '선택안함',
    ...Array.from({ length: 124 }, (_, i) => (2023 - i).toString()),
  ];
  const dropDownData = {
    genderList,
    yearsList,
  };
  const onSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await updateProfile(selectedData);
  };
  useEffect(() => {
    const datafetch = async () => {
      const fetchData = await fetchProfile();
      setProfileData(fetchData.data);
    };
    datafetch();
  }, []);

  return (
    <>
      <Header title="계정 설정" isPrevButton isFilterButton={false} />
      <div className="relative flex-1 overflow-y-auto flex flex-col items-center w-full">
        {profileData && (
          <ProfileContainer
            name={profileData.name}
            email={profileData.email}
            dropDownData={dropDownData}
            setSelectedData={setSelectedData}
            onSubmit={onSubmit}
            fetchSelectedGender={profileData.gender}
            fetchSelectedYear={profileData.yearOfBirth}
          />
        )}
      </div>
      <div className="flex justify-center">
        <Navbar />
      </div>
    </>
  );
}
