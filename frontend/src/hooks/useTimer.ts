import { useEffect, useState } from 'react';

const useTimer = (initialTime: number, interval: number) => {
  const [remainingTime, setRemainingTime] = useState<number>(initialTime);
  const [inactiveTime, setInactiveTime] = useState<number>(0);
  const [stopTime, setStopTime] = useState<number>(0);

  useEffect(() => {
    let from = Date.now();
    let timer: NodeJS.Timeout;

    const handleVisibilityChange = () => {
      const to = Date.now();
      if (document.visibilityState === 'hidden') {
        setInactiveTime(to);
        setStopTime(remainingTime);
      } else {
        const inactiveDuration = inactiveTime
          ? Math.floor(stopTime - (to - inactiveTime) / 1000)
          : 0;
        setRemainingTime(inactiveDuration);
        setInactiveTime(0);
      }
    };
    document.addEventListener('visibilitychange', handleVisibilityChange);

    const calculateRemaining = () => {
      if (remainingTime <= 1) {
        setRemainingTime(0);
        return;
      }
      const to = Date.now();
      const diff = to - from;
      const delay = interval - (diff - interval);

      from = to;
      setRemainingTime((prevStepTime) => prevStepTime - 1);
      timer = setTimeout(calculateRemaining, delay);
    };
    timer = setTimeout(calculateRemaining, interval);

    return () => {
      clearTimeout(timer);
      document.removeEventListener('visibilitychange', handleVisibilityChange);
    };
  }, [remainingTime]);
  return remainingTime;
};

export default useTimer;
