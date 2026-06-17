import { useNavigate } from 'react-router-dom';

import logo from '@/assets/logo.png';

function Header() {
    const navigate = useNavigate();
    return (
        <header className="border-b-primary/20 flex h-24 items-center border-b">
            <img
                src={logo}
                alt="jreg0のロゴ"
                className="h-20"
                onClick={() => navigate('/')}
            />
        </header>
    );
}

export default Header;
